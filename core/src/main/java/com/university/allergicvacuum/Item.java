package com.university.allergicvacuum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for all collectible objects in KEEC.
 *
 * Responsibilities:
 * - Store the item's position
 * - Store the item's size
 * - Store its visible score
 * - Store its hidden allergy value
 * - Know whether it has been collected
 * - Draw itself
 *
 * Specific item types such as Paper, Toy, Can, and Box
 * can extend this class later.
 */
public class Item {

    // =========================
    // Item properties
    // =========================

    protected Vector2 position;

    protected float width;
    protected float height;

    // Score shown to the player
    protected int score;

    // Hidden allergy value
    protected int allergyValue;

    // Whether the vacuum has collected this item
    protected boolean collected;

    // Image used to display the item
    protected Texture texture;


    // =========================
    // Constructor
    // =========================

    public Item(
        float x,
        float y,
        float width,
        float height,
        int score,
        int allergyValue,
        String texturePath
    ) {

        this.position = new Vector2(x, y);

        this.width = width;
        this.height = height;

        this.score = score;
        this.allergyValue = allergyValue;

        this.collected = false;

        this.texture = new Texture(texturePath);
    }


    // =========================
    // Update
    // =========================

    /**
     * Updates the item.
     *
     * Currently the item does not move,
     * but this method allows us to add
     * behavior later.
     */
    public void update(float delta) {

        // Nothing needed for now.
    }


    // =========================
    // Draw
    // =========================

    /**
     * Draws the item if it has not been collected.
     */
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

    /**
     * Marks this item as collected.
     */
    public void collect() {

        collected = true;
    }


    /**
     * Returns whether this item has been collected.
     */
    public boolean isCollected() {

        return collected;
    }


    /**
     * Makes the item appear in the room again.
     *
     * This will be useful after the vacuum sneezes.
     */
    public void eject(float x, float y) {

        position.set(x, y);

        collected = false;
    }


    // =========================
    // Position
    // =========================

    public float getX() {

        return position.x;
    }


    public float getY() {

        return position.y;
    }


    public float getCenterX() {

        return position.x + width / 2f;
    }


    public float getCenterY() {

        return position.y + height / 2f;
    }


    public Vector2 getPosition() {

        return position;
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
    // Score
    // =========================

    public int getScore() {

        return score;
    }


    // =========================
    // Allergy
    // =========================

    /**
     * Returns the hidden allergy value.
     *
     * The UI should NOT display this value
     * to the player during normal gameplay.
     */
    public int getAllergyValue() {

        return allergyValue;
    }


    // =========================
    // Dispose
    // =========================

    /**
     * Releases the item's texture.
     */
    public void dispose() {

        if (texture != null) {

            texture.dispose();
        }
    }
}
