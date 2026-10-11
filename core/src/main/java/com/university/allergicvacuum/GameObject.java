package com.university.allergicvacuum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Parent class of everything in the room that has a position and size,
 * and is updated and drawn every frame (the vacuum and the items).
 */
public abstract class GameObject {

    // Bottom-left corner of the object
    private final Vector2 position;

    private final float width;
    private final float height;

    // Constructor
    protected GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    // =========================
    // Behaviour of each subclass
    // =========================

    /** Updates the object for one frame. */
    public abstract void update(float delta);

    /** Draws the object on the screen. */
    public abstract void render(SpriteBatch batch);

    /** Releases the object's textures. */
    public abstract void dispose();

    // =========================
    // Drawing
    // =========================

    /**
     * Draws the texture inside this object's box without stretching it.
     *
     * @param offsetX  extra horizontal shift, e.g. for shaking
     * @param offsetY  extra vertical shift, e.g. for shaking
     * @param rotation rotation in degrees around the centre of the image
     */
    protected void drawFitted(SpriteBatch batch, Texture texture,
                              float offsetX, float offsetY, float rotation) {

        float scale = Math.min(
            width / texture.getWidth(),
            height / texture.getHeight()
        );

        float drawWidth = texture.getWidth() * scale;
        float drawHeight = texture.getHeight() * scale;

        batch.draw(
            texture,
            position.x + (width - drawWidth) / 2f + offsetX,
            position.y + (height - drawHeight) / 2f + offsetY,
            drawWidth / 2f,
            drawHeight / 2f,
            drawWidth,
            drawHeight,
            1f,
            1f,
            rotation,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
    }

    // =========================
    // Position and size
    // =========================

    /** Only subclasses may change the position vector directly. */
    protected Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
