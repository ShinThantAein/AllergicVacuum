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

    private static final float ITEM_SIZE = ItemFactory.ITEM_SIZE;

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

    // Allergy at which the vacuum sneezes out everything it collected
    private final float maxAllergy;

    // Items in this room
    private final List<Item> items;

    // Read-only view of items, so other classes can't add or remove items
    private final List<Item> readOnlyItems;

    private Texture background;

    // Constructor
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;

        switch (levelNumber) {
            case 1:
                roomName = "Living Room";
                targetScore = 120;
                timeLimit = 60f;
                maxAllergy = 45f;
                break;
            case 2:
                roomName = "Bedroom";
                targetScore = 130;
                timeLimit = 65f;
                maxAllergy = 40f;
                break;
            default:
                // Level 3, the last level
                roomName = "Kitchen";
                targetScore = 120;
                timeLimit = 70f;
                maxAllergy = 40f;
                break;
        }

        this.items = new ArrayList<>();
        this.readOnlyItems = Collections.unmodifiableList(items);
    }

    public void loadBackground() {
        if (background != null) {
            background.dispose();
        }

        switch (levelNumber) {
            case 1:
                background = new Texture("living_room.jpg");
                break;
            case 2:
                background = new Texture("bedroom.jpg");
                break;
            default:
                background = new Texture("kitchen.jpg");
                break;
        }
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

        // In every level, collecting all items gives more allergy than
        // maxAllergy, so the player has to skip some plastic and metal
        // items to win without sneezing.
        switch (levelNumber) {

            case 1:
                // Living Room: 220 score, 80 allergy in total
                addItems(ItemType.PAPER, 2);
                addItems(ItemType.PLASTIC_BAG, 1);
                addItems(ItemType.BOTTLE, 1);
                addItems(ItemType.CAN, 1);
                addItems(ItemType.TOY1, 1);
                addItems(ItemType.BAG, 1);
                addItems(ItemType.BOOK, 1);
                addItems(ItemType.CLOTH, 1);
                addItems(ItemType.BOX, 1);
                break;

            case 2:
                // Bedroom: 300 score, 105 allergy in total
                addItems(ItemType.CLOTH, 1);
                addItems(ItemType.SWEATER, 1);
                addItems(ItemType.BOOK, 1);
                addItems(ItemType.PAPER, 2);
                addItems(ItemType.TOY1, 1);
                addItems(ItemType.TOY2, 1);
                addItems(ItemType.PLASTIC_BAG, 2);
                addItems(ItemType.BOX, 1);
                addItems(ItemType.BAG, 1);
                addItems(ItemType.CAN, 2);
                break;

            default:
                // Level 3, Kitchen: 325 score, 125 allergy in total
                addItems(ItemType.CAN, 2);
                addItems(ItemType.BOTTLE, 1);
                addItems(ItemType.PLASTIC_BAG, 1);
                addItems(ItemType.PAN, 1);
                addItems(ItemType.FRIDGE, 1);
                addItems(ItemType.EGG, 2);
                addItems(ItemType.OIL, 2);
                addItems(ItemType.PAPER, 1);
                addItems(ItemType.BAG, 1);
                addItems(ItemType.TOY3, 1);
                break;
        }

        scatterItems(items, keepClear, false);
    }

    /** Adds items of one type, created by ItemFactory. */
    private void addItems(ItemType type, int count) {
        for (int i = 0; i < count; i++) {
            items.add(ItemFactory.createItem(type));
        }
    }

    /**
     * The vacuum sneezes: every collected item shoots back out of it,
     * and all items fly to new random spots away from the vacuum.
     *
     * @param fromX     centre of the vacuum, where collected items come out
     * @param fromY     centre of the vacuum, where collected items come out
     * @param keepClear area around the vacuum where no item may land
     */
    public void sneezeScatter(float fromX, float fromY, Rectangle keepClear) {
        for (Item item : items) {
            if (item.isCollected()) {
                item.reset();
                item.setPosition(
                    fromX - item.getWidth() / 2f,
                    fromY - item.getHeight() / 2f
                );
            }
        }

        scatterItems(items, keepClear, true);
    }

    /**
     * Moves the given items to random spots on the floor.
     *
     * The floor is split into a grid with at least one cell per item,
     * and each item goes into a different random cell, so items are
     * spread over the whole room instead of bunching up in one area.
     *
     * @param animate if true, items fly to their new spot instead of jumping
     */
    private void scatterItems(List<Item> toPlace, Rectangle keepClear, boolean animate) {
        if (toPlace.isEmpty()) {
            return;
        }

        float floorX = Gdx.graphics.getWidth() * FLOOR_LEFT;
        float floorY = Gdx.graphics.getHeight() * FLOOR_BOTTOM;
        float floorWidth = Gdx.graphics.getWidth() * (FLOOR_RIGHT - FLOOR_LEFT);
        float floorHeight = Gdx.graphics.getHeight() * (FLOOR_TOP - FLOOR_BOTTOM);

        // Pick a grid shape that roughly matches the floor's proportions
        int columns = Math.max(1, MathUtils.ceil(
            (float) Math.sqrt(toPlace.size() * floorWidth / floorHeight)
        ));
        int rows = Math.max(1, MathUtils.ceil((float) toPlace.size() / columns));

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
        for (Item item : toPlace) {
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

            if (animate) {
                item.flyTo(spot.x, spot.y);
            } else {
                item.setPosition(spot.x, spot.y);
            }
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

    // Get all items in this level (read-only list)
    public List<Item> getItems() {
        return readOnlyItems;
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

    // Get the allergy that makes the vacuum sneeze
    public float getMaxAllergy() {
        return maxAllergy;
    }

    // Reset collected items and scatter them again for replaying this room
    public void resetLevel(Rectangle keepClear) {
        for (Item item : items) {
            item.reset();
        }

        scatterItems(items, keepClear, false);
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
