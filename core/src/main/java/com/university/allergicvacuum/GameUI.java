package com.university.allergicvacuum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameUI {

    // Game information
    private int score;
    private float allergy;
    private float maxAllergy;

    private float time;
    private int currentLevel;
    private int targetScore;


    // Constructor
    public GameUI(
        int currentLevel,
        int targetScore,
        float time,
        float maxAllergy
    ) {

        this.currentLevel = currentLevel;
        this.targetScore = targetScore;

        this.time = time;

        this.score = 0;

        this.allergy = 0;
        this.maxAllergy = maxAllergy;
    }


    // Update
    public void update(float delta) {

        time -= delta;

        if (time < 0) {
            time = 0;
        }
    }


    // Score
    public void setScore(int score) {

        this.score = score;
    }


    public int getScore() {

        return score;
    }


    // Allergy
    public void setAllergy(float allergy) {

        this.allergy = allergy;
    }


    public float getAllergy() {

        return allergy;
    }


    public float getMaxAllergy() {

        return maxAllergy;
    }


    // Timer
    public float getTime() {

        return time;
    }


    public void setTime(float time) {

        this.time = time;
    }


    // Level
    public int getCurrentLevel() {

        return currentLevel;
    }


    public void setCurrentLevel(int currentLevel) {

        this.currentLevel = currentLevel;
    }


    // Target Score
    public int getTargetScore() {

        return targetScore;
    }


    public void setTargetScore(int targetScore) {

        this.targetScore = targetScore;
    }


    // =========================
    // Game State
    // =========================

    /**
     * Checks whether the player
     * has completed the level.
     */
    public boolean isLevelComplete() {

        return score >= targetScore;
    }


    /**
     * Checks whether the game
     * is over because time ran out.
     */
    public boolean isGameOver() {

        return time <= 0 && score < targetScore;
    }


    // =========================
    // Drawing
    // =========================

    /**
     * Draws the game information
     * on the screen.
     */
    public void render(SpriteBatch batch) {

        // UI drawing will be added here.
        // Score, allergy, timer,
        // level and target score will be displayed.
    }
}
