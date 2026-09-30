package com.university.allergicvacuum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Vacuum {

    // Vacuum properties
    private final Vector2 position;

    private final float width;
    private final float height;

    private float speed;

    // Suction area around the vacuum
    private final Circle suctionArea;

    // Visual representation
    private final Texture texture;


    // Constructor
    public Vacuum(float x, float y, float width, float height, float speed) {

        this.position = new Vector2(x, y);

        this.width = width;
        this.height = height;

        this.speed = speed;

        // Suction radius
        float suctionRadius = width * 1.5f;

        this.suctionArea = new Circle(
            x + width / 2f,
            y + height / 2f,
            suctionRadius
        );

        // Temporary vacuum image
        this.texture = new Texture("vacuum.png");
    }


    // Update
    public void update(float delta) {

        move(delta);

        updateSuctionArea();
    }



    // Movement
    /*** Controls the vacuum using WASD or arrow keys. */
    private void move(float delta) {

        float movementX = 0;
        float movementY = 0;

        // Left
        if (Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            movementX -= speed * delta;
        }

        // Right
        if (Gdx.input.isKeyPressed(Input.Keys.D)
            || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            movementX += speed * delta;
        }

        // Up
        if (Gdx.input.isKeyPressed(Input.Keys.W)
            || Gdx.input.isKeyPressed(Input.Keys.UP)) {

            movementY += speed * delta;
        }

        // Down
        if (Gdx.input.isKeyPressed(Input.Keys.S)
            || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            movementY -= speed * delta;
        }

        position.x += movementX;
        position.y += movementY;
    }


    // Suction
    /**Updates the position of the suction area,nso that it follows the vacuum.*/
    private void updateSuctionArea() {

        suctionArea.setPosition(
            position.x + width / 2f,
            position.y + height / 2f
        );
    }


    /**
     * Checks all items and collects the items
     * inside the suction area.
     *
     * @param items list of items currently in the room
     */
    public void suction(List<Item> items) {

        for (Item item : items) {

            if (item == null || item.isCollected()) {
                continue;
            }

            if (isInsideSuctionArea(item)) {

                collect(item);
            }
        }
    }


    /**
     * Checks whether an item is inside
     * the vacuum's suction area.
     */
    private boolean isInsideSuctionArea(Item item) {

        return suctionArea.contains(
            item.getCenterX(),
            item.getCenterY()
        );
    }


    /**
     * Collects an item.
     *
     * The Vacuum only tells the Item that it
     * has been collected.
     *
     * Score and allergy handling should be
     * managed by the appropriate systems.
     */
    private void collect(Item item) {

        item.collect();
    }


    // =========================
    // Drawing
    // =========================

    /** Draws the vacuum on the screen */
    public void render(SpriteBatch batch) {

        batch.draw(
            texture,
            position.x,
            position.y,
            width,
            height
        );
    }

    // Getters
    public Vector2 getPosition() {
        return position;
    }


    public float getX() {
        return position.x;
    }


    public float getY() {
        return position.y;
    }


    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }


    public float getSpeed() {
        return speed;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public Circle getSuctionArea() {
        return suctionArea;
    }


    // =========================
    // Dispose
    // =========================

    /**
     * Releases the texture when the vacuum
     * is no longer needed.
     */
    public void dispose() {

        texture.dispose();
    }
}

