package com.university.allergicvacuum;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Level {

    // Level information
    private final int levelNumber;
    private final String roomName;
    private final int targetScore;
    private final float timeLimit;

    // Items in this room
    private final List<Item> items;
    private Texture background;

    // Constructor
    public Level(
        int levelNumber,
        String roomName,
        int targetScore,
        float timeLimit
    ) {
        this.levelNumber = levelNumber;
        this.roomName = roomName;
        this.targetScore = targetScore;
        this.timeLimit = timeLimit;

        this.items = new ArrayList<>();
    }

    public void loadBackground() {
        if (background != null) {
            background.dispose();
        }

        background = new Texture("living_room.jpg");
    }

    public void renderBackground(SpriteBatch batch) {
        if (background != null) {
            batch.draw(
                background,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
            );
        }
    }

    // Load items for this room
    public void loadLevel() {

        // Avoid adding duplicate items if the level is loaded again
        if (!items.isEmpty()) {
            return;
        }

        switch (levelNumber) {

            case 1:
                // Living Room
                items.add(new Item(
                    200, 200, 50, 50,
                    "Paper", 10, 5
                ));

                items.add(new Item(
                    600, 350, 50, 50,
                    "Plastic Bag", 20, 10
                ));

                items.add(new Item(
                    700, 150, 50, 50,
                    "Can", 30, 15
                ));

                items.add(new Item(
                    300, 450, 50, 50,
                    "Toy", 40, 20
                ));

                items.add(new Item(
                    500, 200, 50, 50,
                    "Paper", 10, 5
                ));
                break;

            case 2:
                // Bedroom
                items.add(new Item(
                    150, 180, 50, 50,
                    "Toy1", 20, 10
                ));

                items.add(new Item(
                    350, 400, 50, 50,
                    "Paper", 10, 5
                ));

                items.add(new Item(
                    600, 250, 50, 50,
                    "Plastic Bag", 20, 10
                ));

                items.add(new Item(
                    800, 400, 50, 50,
                    "Toy2", 40, 20
                ));

                items.add(new Item(
                    700, 150, 50, 50,
                    "Can", 30, 15
                ));
                break;

            case 3:
                // Kitchen
                items.add(new Item(
                    180, 300, 50, 50,
                    "Can", 30, 15
                ));

                items.add(new Item(
                    350, 180, 50, 50,
                    "Plastic Bag", 20, 10
                ));

                items.add(new Item(
                    550, 400, 50, 50,
                    "Paper", 10, 5
                ));

                items.add(new Item(
                    750, 250, 50, 50,
                    "Can", 30, 15
                ));

                items.add(new Item(
                    850, 400, 50, 50,
                    "Toy3", 40, 20
                ));
                break;

            default:
                // Fallback room for levels without a defined layout
                items.add(new Item(
                    200, 200, 50, 50,
                    "Paper", 10, 5
                ));

                items.add(new Item(
                    400, 350, 50, 50,
                    "Plastic Bag", 20, 10
                ));

                items.add(new Item(
                    650, 200, 50, 50,
                    "Can", 30, 15
                ));
                break;
        }
    }

    // Get all items in this level
    public List<Item> getItems() {
        return items;
    }

    // Get level number
    public int getLevelNumber() {
        return levelNumber;
    }

    // Get room name
    public String getRoomName() {
        return roomName;
    }

    // Get target score
    public int getTargetScore() {
        return targetScore;
    }

    // Get time limit
    public float getTimeLimit() {
        return timeLimit;
    }

    // Reset collected items for replaying this room
    public void resetLevel() {
        for (Item item : items) {
            item.reset();
        }
    }

    // Release item textures
    public void dispose() {
        if (background != null) {
            background.dispose();
            background = null;
        }

        for (Item item : items) {
            item.dispose();
        }

        items.clear();
    }
}
