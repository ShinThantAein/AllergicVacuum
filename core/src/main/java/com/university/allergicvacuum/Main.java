package com.university.allergicvacuum;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game class.
 *
 * Level 1 - Living Room
 *
 * Currently tests:
 * - Vacuum movement
 * - Item rendering
 * - Vacuum suction
 * - Item collection
 */
public class Main implements ApplicationListener {

    private SpriteBatch batch;

    private Vacuum vacuum;

    private List<Item> items;


    @Override
    public void create() {

        // Create SpriteBatch
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
        // Create Level 1 Items
        // Living Room
        // =========================

        items = new ArrayList<>();


        // Paper 1
        items.add(
            new Item(
                200,        // x
                200,        // y
                50,         // width
                50,         // height
                "Paper",    // type
                10,         // score
                5           // allergy
            )
        );


        // Paper 2
        items.add(
            new Item(
                600,        // x
                350,        // y
                50,         // width
                50,         // height
                "Paper",    // type
                10,         // score
                5           // allergy
            )
        );


        // Paper 3
        items.add(
            new Item(
                700,        // x
                150,        // y
                50,         // width
                50,         // height
                "Paper",    // type
                10,         // score
                5           // allergy
            )
        );


        // Plastic Bag
        items.add(
            new Item(
                300,              // x
                350,              // y
                50,               // width
                50,               // height
                "Plastic Bag",    // type
                20,               // score
                10                // allergy
            )
        );


        // Can
        items.add(
            new Item(
                600,        // x
                150,        // y
                50,         // width
                50,         // height
                "Can",      // type
                30,         // score
                15          // allergy
            )
        );


        // Toy
        items.add(
            new Item(
                300,        // x
                450,        // y
                50,         // width
                50,         // height
                "Toy",      // type
                40,         // score
                20          // allergy
            )
        );
    }


    @Override
    public void render() {

        // =========================
        // Clear Screen
        // =========================

        Gdx.gl.glClearColor(
            0.15f,
            0.15f,
            0.15f,
            1
        );

        Gdx.gl.glClear(
            GL20.GL_COLOR_BUFFER_BIT
        );


        // =========================
        // Update Vacuum
        // =========================

        float delta = Gdx.graphics.getDeltaTime();

        vacuum.update(delta);


        // =========================
        // Check Suction
        // =========================

        vacuum.suction(items);


        // =========================
        // Draw Everything
        // =========================

        batch.begin();


        // Draw items
        for (Item item : items) {
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


        // Dispose items
        if (items != null) {

            for (Item item : items) {
                item.dispose();
            }
        }


        // Dispose SpriteBatch
        if (batch != null) {
            batch.dispose();
        }
    }
}
