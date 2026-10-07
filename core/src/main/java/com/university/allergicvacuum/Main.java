package com.university.allergicvacuum;

<<<<<<< HEAD
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class Main implements ApplicationListener {

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont popupFont;

    private Vacuum vacuum;
    private Level currentLevel;
    private GameUI gameUI;

    private enum GameState {
        PLAYING,
        WON,
        LOST
    }
=======
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
>>>>>>> feature/level-system

    private GameState gameState = GameState.PLAYING;
    private Rectangle buttonBounds;

    private int levelNumber = 1;
    private int targetScore = 50;
    private float levelTime = 60f;
    private float maxAllergy = 100f;

    private static final float VACUUM_START_X = 400f;
    private static final float VACUUM_START_Y = 250f;

<<<<<<< HEAD
    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        popupFont = new BitmapFont();
        popupFont.getData().setScale(1.5f);

        buttonBounds = new Rectangle();

        vacuum = new Vacuum(
            VACUUM_START_X,
            VACUUM_START_Y,
            80,
            80,
            250
        );

        gameUI = new GameUI(
            levelNumber,
            targetScore,
            levelTime,
            maxAllergy
        );

        loadLevel();
=======
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
    /**Updates the position of the suction area, so that it follows the vacuum.*/
    private void updateSuctionArea() {

        suctionArea.setPosition(
            position.x + width / 2f,
            position.y + height / 2f
        );
>>>>>>> feature/level-system
    }

    private void loadLevel() {
        // Create the current room.
        String roomName;

        switch (levelNumber) {
            case 1:
                roomName = "Living Room";
                break;
            case 2:
                roomName = "Bedroom";
                break;
            case 3:
                roomName = "Kitchen";
                break;
            default:
                roomName = "Living Room";
                break;
        }

        currentLevel = new Level(
            levelNumber,
            roomName,
            targetScore,
            levelTime
        );

        currentLevel.loadLevel();
        currentLevel.loadBackground();
    }

<<<<<<< HEAD
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameState == GameState.PLAYING) {
            updateGame(Gdx.graphics.getDeltaTime());
        }

        batch.begin();

        // Draw background first.
        currentLevel.renderBackground(batch);

        // Draw collectible objects.
        for (Item item : currentLevel.getItems()) {
            item.render(batch);
        }

        // Draw player and HUD.
        vacuum.render(batch);
        gameUI.render(batch);

        batch.end();

        if (gameState != GameState.PLAYING) {
            renderPopup();
            handlePopupInput();
        }
    }

    private void updateGame(float delta) {
        vacuum.update(delta);

        List<Item> items = currentLevel.getItems();

        // Collect nearby items and add their allergy values.
        int allergyAdded = vacuum.suction(items);
        gameUI.addAllergy(allergyAdded);

        // Calculate score from collected items.
        int totalScore = 0;

        for (Item item : items) {
            if (item.isCollected()) {
                totalScore += item.getScore();
            }
        }

        gameUI.setScore(totalScore);
        gameUI.update(delta);

        // Decide whether the level has ended.
        if (gameUI.isLevelComplete()) {
            gameState = GameState.WON;
        } else if (gameUI.isGameOver()
            || gameUI.getAllergy() >= gameUI.getMaxAllergy()) {
            gameState = GameState.LOST;
        }
    }

    private void renderPopup() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float panelWidth = 440f;
        float panelHeight = 240f;

        float panelX = (screenWidth - panelWidth) / 2f;
        float panelY = (screenHeight - panelHeight) / 2f;

        float buttonWidth = 240f;
        float buttonHeight = 55f;

        buttonBounds.set(
            (screenWidth - buttonWidth) / 2f,
            panelY + 30f,
            buttonWidth,
            buttonHeight
        );

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(
            GL20.GL_SRC_ALPHA,
            GL20.GL_ONE_MINUS_SRC_ALPHA
        );

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Dark overlay.
        shapes.setColor(0f, 0f, 0f, 0.7f);
        shapes.rect(0, 0, screenWidth, screenHeight);

        // Popup panel.
        shapes.setColor(0.12f, 0.12f, 0.12f, 1f);
        shapes.rect(panelX, panelY, panelWidth, panelHeight);

        // Button color.
        if (gameState == GameState.WON) {
            shapes.setColor(0.15f, 0.55f, 0.25f, 1f);
        } else {
            shapes.setColor(0.7f, 0.15f, 0.15f, 1f);
        }

        shapes.rect(
            buttonBounds.x,
            buttonBounds.y,
            buttonBounds.width,
            buttonBounds.height
        );

        shapes.end();

        batch.begin();

        popupFont.getData().setScale(1.5f);
        popupFont.setColor(1f, 1f, 1f, 1f);

        String title = gameState == GameState.WON
            ? "You Win!!"
            : "You Lose!!";

        String message = gameState == GameState.WON
            ? "You reached the target score!"
            : "Time or allergy limit reached.";

        String buttonText = gameState == GameState.WON
            ? "Next Level"
            : "Play Again";

        popupFont.draw(
            batch,
            title,
            panelX + 125f,
            panelY + panelHeight - 55f
        );

        popupFont.getData().setScale(1f);

        popupFont.draw(
            batch,
            message,
            panelX + 35f,
            panelY + panelHeight - 100f
        );

        popupFont.draw(
            batch,
            buttonText,
            buttonBounds.x + 65f,
            buttonBounds.y + 33f
        );

        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void handlePopupInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (buttonBounds.contains(mouseX, mouseY)) {
            if (gameState == GameState.WON) {
                startNextLevel();
            } else {
                restartLevel();
            }
        }
    }

    private void startNextLevel() {
        // Release resources belonging to the previous room.
        currentLevel.dispose();

        levelNumber++;
        targetScore += 50;
        levelTime = 60f;

        // Load the next room and reset the game.
        loadLevel();

        vacuum.getPosition().set(VACUUM_START_X, VACUUM_START_Y);

        gameUI.resetForLevel(
            levelNumber,
            targetScore,
            levelTime,
            maxAllergy
        );

        gameState = GameState.PLAYING;
    }

    private void restartLevel() {
        // Reset the current room's collected items.
        currentLevel.resetLevel();

        vacuum.getPosition().set(VACUUM_START_X, VACUUM_START_Y);

        gameUI.resetForLevel(
            levelNumber,
            targetScore,
            levelTime,
            maxAllergy
        );

        gameState = GameState.PLAYING;
    }

    @Override
    public void resize(int width, int height) {
        // Background is stretched to the current screen size.
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (currentLevel != null) {
            currentLevel.dispose();
        }

        if (vacuum != null) {
            vacuum.dispose();
        }

        if (gameUI != null) {
            gameUI.dispose();
=======
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
>>>>>>> feature/level-system
        }
    }

<<<<<<< HEAD
        if (batch != null) {
            batch.dispose();
        }

        if (shapes != null) {
            shapes.dispose();
        }

        if (popupFont != null) {
            popupFont.dispose();
        }
=======

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
>>>>>>> feature/level-system
    }
}
