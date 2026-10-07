package com.university.allergicvacuum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Item {

    // Item position and size
    private final Vector2 position;
    private final float width;
    private final float height;

    // Item information
    private final String type;
    private final int score;
    private final int allergyValue;

    // Item image
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


        // Choose the correct image
        if (type.equals("Paper")) {

            texture = new Texture("paper.jpg");

        } else if (type.equals("Plastic Bag")) {

            texture = new Texture("plasticbag.jpg");

        } else if (type.equals("Can")) {

            texture = new Texture("can.jpg");

        } else if (type.equals("Toy")) {

            texture = new Texture("toy1.jpg");

        } else if (type.equals("Toy1")) {

            texture = new Texture("toy1.jpg");

        } else if (type.equals("Toy2")) {

            texture = new Texture("toy2.jpg");

        } else if (type.equals("Toy3")) {

            texture = new Texture("toy3.jpg");

        } else {

            // Default image
            texture = new Texture("paper.jpg");
        }
    }


    // =========================
    // Draw Item
    // =========================

    public void render(SpriteBatch batch) {

        if (!collected) {

            batch.draw(
                texture,
                position.x,
                position.y,
                width,
                height
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

        texture.dispose();
    }
}
