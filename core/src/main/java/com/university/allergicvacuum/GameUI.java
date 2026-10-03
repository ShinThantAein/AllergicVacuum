package com.university.allergicvacuum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class GameUI extends ApplicationAdapter {

    private SpriteBatch batch;

    private Vacuum vacuum;

    private List<Item> items;

    @Override
    public void create() {

        batch = new SpriteBatch();

        // Create the vacuum
        vacuum = new Vacuum(
            400,
            250,
            80,
            80,
            250
        );

        // Create Level 1 items
        items = new ArrayList<>();

        Item paper = new Item(
            200, 200,
            50, 50,
            "Paper",
            10,
            5
        );

        Item plasticBag = new Item(
            400, 300,
            50, 50,
            "Plastic Bag",
            20,
            10
        );

        Item can = new Item(
            600, 200,
            50, 50,
            "Can",
            30,
            15
        );

        Item toy = new Item(
            300, 400,
            50, 50,
            "Toy",
            40,
            20
        );

        // Add items to the list
        items.add(paper);
        items.add(plasticBag);
        items.add(can);
        items.add(toy);
    }

    @Override
    public void render() {

        float delta =
            com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        // Move the vacuum
        vacuum.update(delta);

        // Check if vacuum collects items
        vacuum.suction(items);

        batch.begin();

        // Draw the items
        for (Item item : items) {
            item.render(batch);
        }

        // Draw the vacuum
        vacuum.render(batch);

        batch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();

        vacuum.dispose();

        for (Item item : items) {
            item.dispose();
        }
    }
}
