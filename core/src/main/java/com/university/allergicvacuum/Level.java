package com.university.allergicvacuum;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main game class.
 *
 * This is currently used to test:
 * - Vacuum movement
 * - Level loading
 * - Item rendering
 * - Vacuum suction
 * - Item collection
 */
public class Main implements ApplicationListener {

    private SpriteBatch batch;

    private Vacuum vacuum;

    private Level level;


    @Override
    public void create() {

        // =========================
        // Create SpriteBatch
        // =========================

        batch = new SpriteBatch();


        // =========================
        // Create Vacuum
        // =========================

        vacuum = new Vacuum(
            400,    // x
            250,    // y
            80,     // width
            80,     // height
            250     // movement speed
        );


        // =========================
        // Create Level
        // =========================

        level = new Level(
            1,              // level number
            "Living Room",  // room name
            50,             // target score
            60              // time limit
        );


        // =========================
        // Load Level
        // =========================

        level.loadLevel();
    }


    @Override
    public void render() {

        // =========================
        // Clear the screen
        // =========================

        Gdx.gl.glClearColor(
            0.15f,
            0.15f,
            0.15f,
            1
        );

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // =========================
        // Get Delta Time
        // =========================

        float delta = Gdx.graphics.getDeltaTime();


        // =========================
        // Update Vacuum
        // =========================

        vacuum.update(delta);


        // =========================
        // Check Suction
        // =========================

        vacuum.suction(level.getItems());


        // =========================
        // Draw
        // =========================

        batch.begin();


        // Draw items from current level
        for (Item item : level.getItems()) {

            item.render(batch);
        }


        // Draw vacuum
        vacuum.render(batch);


        batch.end();
    }


    @Override
    public void resize(int width, int height) {

        if (width <= 0 || height <= 0) {
            return;
        }
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {

        // Dispose vacuum
        if (vacuum != null) {

            vacuum.dispose();
        }


        // Dispose level and its items
        if (level != null) {

            level.dispose();
        }


        // Dispose SpriteBatch
        if (batch != null) {

            batch.dispose();
        }
    }
}
