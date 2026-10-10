package com.university.allergicvacuum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Level {

    // Width and height of the box each item is drawn in
    private static final float ITEM_SIZE = 62f;

    // Floor area of the room background, as fractions of the screen size
    private static final float FLOOR_LEFT = 0.18f;
    private static final float FLOOR_RIGHT = 0.86f;
    private static final float FLOOR_BOTTOM = 0.14f;
    private static final float FLOOR_TOP = 0.74f;

    // Minimum empty space kept between scattered items
    private static final float ITEM_SPACING = 20f;
    private static final int PLACEMENT_ATTEMPTS = 30;

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

    /**
     * Creates the items for this room and scatters them
     * randomly across the floor.
     *
     * @param keepClear area where no item may be placed,
     *                  such as the vacuum's starting spot
     */
    public void loadLevel(Rectangle keepClear) {

        // Avoid adding duplicate items if the level is loaded again
        if (!items.isEmpty()) {
            return;
        }

        switch (levelNumber) {

            case 1:
                // Living Room
                addItem("Paper", 10, 5);
                addItem("Paper", 10, 5);
                addItem("Plastic Bag", 20, 10);
                addItem("Can", 30, 15);
                addItem("Toy", 40, 20);
                addItem("Bag", 20, 10);
                addItem("Book", 30, 15);
                addItem("Cloth", 20, 10);
                break;

            case 2:
                // Bedroom
                addItem("Toy1", 20, 10);
                addItem("Paper", 10, 5);
                addItem("Plastic Bag", 20, 10);
                addItem("Toy2", 40, 20);
                addItem("Can", 30, 15);
                break;

            case 3:
                // Kitchen
                addItem("Can", 30, 15);
                addItem("Plastic Bag", 20, 10);
                addItem("Paper", 10, 5);
                addItem("Can", 30, 15);
                addItem("Toy3", 40, 20);
                break;

            default:
                // Fallback room for levels without a defined layout
                addItem("Paper", 10, 5);
                addItem("Plastic Bag", 20, 10);
                addItem("Can", 30, 15);
                break;
        }

        scatterItems(keepClear);
    }

    private void addItem(String type, int score, int allergyValue) {
        items.add(new Item(
            0, 0, ITEM_SIZE, ITEM_SIZE,
            type, score, allergyValue
        ));
    }

    /**
     * Moves every item to a random spot on the floor.
     *
     * The floor is split into a grid with at least one cell per item,
     * and each item goes into a different random cell, so items are
     * spread over the whole room instead of bunching up in one area.
     */
    private void scatterItems(Rectangle keepClear) {
        float floorX = Gdx.graphics.getWidth() * FLOOR_LEFT;
        float floorY = Gdx.graphics.getHeight() * FLOOR_BOTTOM;
        float floorWidth = Gdx.graphics.getWidth() * (FLOOR_RIGHT - FLOOR_LEFT);
        float floorHeight = Gdx.graphics.getHeight() * (FLOOR_TOP - FLOOR_BOTTOM);

        // Pick a grid shape that roughly matches the floor's proportions
        int columns = Math.max(1, MathUtils.ceil(
            (float) Math.sqrt(items.size() * floorWidth / floorHeight)
        ));
        int rows = Math.max(1, MathUtils.ceil((float) items.size() / columns));

        float cellWidth = floorWidth / columns;
        float cellHeight = floorHeight / rows;

        List<Rectangle> cells = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells.add(new Rectangle(
                    floorX + column * cellWidth,
                    floorY + row * cellHeight,
                    cellWidth,
                    cellHeight
                ));
            }
        }

        Collections.shuffle(cells);

        Rectangle spot = new Rectangle();
        int nextCell = 0;

        for (Item item : items) {
            boolean placed = false;

            while (!placed && nextCell < cells.size()) {
                placed = pickSpotInCell(cells.get(nextCell), keepClear, spot);
                nextCell++;
            }

            // More items than free cells: fall back to anywhere on the floor
            if (!placed) {
                pickSpotInCell(
                    new Rectangle(floorX, floorY, floorWidth, floorHeight),
                    null,
                    spot
                );
            }

            item.getPosition().set(spot.x, spot.y);
        }
    }

    /**
     * Picks a random item position inside the cell that does not
     * touch keepClear. Returns false if no such position was found.
     */
    private boolean pickSpotInCell(Rectangle cell, Rectangle keepClear, Rectangle spot) {
        float minX = cell.x + ITEM_SPACING / 2f;
        float minY = cell.y + ITEM_SPACING / 2f;
        float maxX = Math.max(minX, cell.x + cell.width - ITEM_SIZE - ITEM_SPACING / 2f);
        float maxY = Math.max(minY, cell.y + cell.height - ITEM_SIZE - ITEM_SPACING / 2f);

        for (int attempt = 0; attempt < PLACEMENT_ATTEMPTS; attempt++) {
            spot.set(
                MathUtils.random(minX, maxX),
                MathUtils.random(minY, maxY),
                ITEM_SIZE,
                ITEM_SIZE
            );

            if (keepClear == null || !spot.overlaps(keepClear)) {
                return true;
            }
        }

        return false;
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

    // Reset collected items and scatter them again for replaying this room
    public void resetLevel(Rectangle keepClear) {
        for (Item item : items) {
            item.reset();
        }

        scatterItems(keepClear);
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
