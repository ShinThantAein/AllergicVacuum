package com.university.allergicvacuum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/** An item on the floor that the vacuum can collect. Create items with ItemFactory. */
public class Item extends GameObject {

    // Textures shared by all items, with how many items use each one
    private static final Map<String, Texture> sharedTextures = new HashMap<>();
    private static final Map<String, Integer> textureUsers = new HashMap<>();

    // Item information
    private final ItemType type;
    private final int score;
    private final int allergyValue;

    // Item image
    private final Texture texture;

    // Checks if the item has been collected
    private boolean collected;

    // Flight to a new spot after the vacuum sneezes
    private static final float FLIGHT_DURATION = 0.7f;
    private final Vector2 flightStart = new Vector2();
    private final Vector2 flightEnd = new Vector2();
    private float flightElapsed;
    private boolean flying;
    private float rotation;

    // Constructor
    public Item(float x, float y, float width, float height,
                ItemType type, int score, int allergyValue) {

        super(x, y, width, height);

        this.type = type;
        this.score = score;
        this.allergyValue = allergyValue;

        this.collected = false;

        this.texture = acquireTexture(type.getTextureFile());
    }


    // =========================
    // Textures
    // =========================

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

    @Override
    public void render(SpriteBatch batch) {

        if (!collected) {
            drawFitted(batch, texture, 0f, 0f, rotation);
        }
    }


    // =========================
    // Flight
    // =========================

    /** Starts flying from the current position to (x, y). */
    public void flyTo(float x, float y) {

        flightStart.set(getPosition());
        flightEnd.set(x, y);
        flightElapsed = 0f;
        flying = true;
    }


    @Override
    public void update(float delta) {

        if (!flying) {
            return;
        }

        flightElapsed += delta;
        float progress = Math.min(1f, flightElapsed / FLIGHT_DURATION);

        getPosition().set(flightStart).lerp(
            flightEnd,
            Interpolation.pow2Out.apply(progress)
        );

        // One full spin while flying
        rotation = 360f * progress;

        if (progress >= 1f) {
            flying = false;
            rotation = 0f;
        }
    }


    /** Items can't be collected while they are flying. */
    public boolean isFlying() {

        return flying;
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
    // Item Information
    // =========================

    public ItemType getType() {

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
        flying = false;
        rotation = 0f;
    }

    // =========================
    // Dispose
    // =========================

    @Override
    public void dispose() {

        releaseTexture(type.getTextureFile());
    }
}
