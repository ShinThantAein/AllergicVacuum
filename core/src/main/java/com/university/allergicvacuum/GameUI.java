package com.university.allergicvacuum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameUI {

    private int score;
    private float allergy;
    private float maxAllergy;

    private float time;
    private int currentLevel;
    private int targetScore;

    private final BitmapFont font;

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

        // LibGDX's default font; no image file needed.
        font = new BitmapFont();
    }

    public void addAllergy(int amount) {
        allergy += amount;

        if (allergy > maxAllergy) {
            allergy = maxAllergy;
        }
    }

    public void update(float delta) {
        time -= delta;

        if (time < 0) {
            time = 0;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setAllergy(float allergy) {
        this.allergy = allergy;
    }

    public float getAllergy() {
        return allergy;
    }

    public float getMaxAllergy() {
        return maxAllergy;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public boolean isLevelComplete() {
        return score >= targetScore;
    }

    public boolean isGameOver() {
        return time <= 0 && score < targetScore;
    }

    public void resetForLevel(int newLevel, int newTargetScore,
                              float newTime, float newMaxAllergy) {
        currentLevel = newLevel;
        targetScore = newTargetScore;
        time = newTime;
        maxAllergy = newMaxAllergy;
        score = 0;
        allergy = 0;
    }

    public void render(SpriteBatch batch) {
        float top = Gdx.graphics.getHeight() - 20;

        font.draw(batch, "Level: " + currentLevel, 20, top);
        font.draw(batch, "Score: " + score, 20, top - 25);
        font.draw(batch, "Target: " + targetScore, 20, top - 50);
        font.draw(batch, "Time: " + (int) time, 20, top - 75);
        font.draw(
            batch,
            "Allergy: " + (int) allergy + " / " + (int) maxAllergy,
            20,
            top - 100
        );
    }

    public void dispose() {
        font.dispose();
    }
}
