package com.university.allergicvacuum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Item {

    // Textures shared by all items, with how many items use each one
    private static final Map<String, Texture> sharedTextures = new HashMap<>();
    private static final Map<String, Integer> textureUsers = new HashMap<>();

    // Item position and size
    private final Vector2 position;
    private final float width;
    private final float height;

    // Item information
    private final String type;
    private final int score;
    private final int allergyValue;

    // Item image
    private final String textureFile;
    private final Texture texture;

    // Checks if the item has been collected
    private boolean collected;

    // Constructor
    public Item(float x, float y, float width, float height,
                String type, int score, int allergyValue) {

        this.position = new Vector2(x, y);

        this.width = width;
        this.height = height;

        this.type = type;
        this.score = score;
        this.allergyValue = allergyValue;

        this.collected = false;


        this.textureFile = textureFileFor(type);
        this.texture = acquireTexture(textureFile);
    }


    // =========================
    // Textures
    // =========================

    private static String textureFileFor(String type) {

        switch (type) {
            case "Plastic Bag":
                return "sprites/plasticbag.png";
            case "Can":
                return "sprites/can.png";
            case "Toy":
            case "Toy1":
                return "sprites/toy1.png";
            case "Toy2":
                return "sprites/toy2.png";
            case "Toy3":
                return "sprites/toy3.png";
            case "Bag":
                return "sprites/bag.png";
            case "Book":
                return "sprites/book.png";
            case "Cloth":
                return "sprites/cloth.png";
            case "Paper":
            default:
                return "sprites/paper.png";
        }
    }


    private static Texture acquireTexture(String file) {

        Texture shared = sharedTextures.get(file);

        if (shared == null) {
            // Mipmaps keep the image smooth when drawn smaller than its file size
            shared = new Texture(Gdx.files.internal(file), true);
            shared.setFilter(
                Texture.TextureFilter.MipMapLinearLinear,
                Texture.TextureFilter.Linear
            );
            sharedTextures.put(file, shared);
            textureUsers.put(file, 0);
        }

        textureUsers.put(file, textureUsers.get(file) + 1);
        return shared;
    }


    private static void releaseTexture(String file) {

        Integer users = textureUsers.get(file);

        if (users == null) {
            return;
        }

        if (users > 1) {
            textureUsers.put(file, users - 1);
            return;
        }

        sharedTextures.remove(file).dispose();
        textureUsers.remove(file);
    }


    // =========================
    // Draw Item
    // =========================

    public void render(SpriteBatch batch) {

        if (!collected) {

            // Fit the image inside the item box without stretching it
            float scale = Math.min(
                width / texture.getWidth(),
                height / texture.getHeight()
            );

            float drawWidth = texture.getWidth() * scale;
            float drawHeight = texture.getHeight() * scale;

            batch.draw(
                texture,
                position.x + (width - drawWidth) / 2f,
                position.y + (height - drawHeight) / 2f,
                drawWidth,
                drawHeight
            );
        }
    }


    // =========================
    // Collection
    // =========================

    public void collect() {

        collected = true;
    }


    public boolean isCollected() {

        return collected;
    }


    // =========================
    // Position
    // =========================

    public float getCenterX() {

        return position.x + width / 2f;
    }


    public float getCenterY() {

        return position.y + height / 2f;
    }


    public Vector2 getPosition() {

        return position;
    }


    public float getX() {

        return position.x;
    }


    public float getY() {

        return position.y;
    }


    // =========================
    // Size
    // =========================

    public float getWidth() {

        return width;
    }


    public float getHeight() {

        return height;
    }


    // =========================
    // Item Information
    // =========================

    public String getType() {

        return type;
    }


    public int getScore() {

        return score;
    }


    public int getAllergyValue() {

        return allergyValue;
    }

    public void reset() {
        collected = false;
    }

    // =========================
    // Dispose
    // =========================

    public void dispose() {

        releaseTexture(textureFile);
    }
}
