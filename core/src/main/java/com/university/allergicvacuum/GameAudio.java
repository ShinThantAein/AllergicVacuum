package com.university.allergicvacuum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/** Background music and sound effects for the game. */
public class GameAudio {

    private static final float MUSIC_VOLUME = 0.4f;
    private static final float EFFECT_VOLUME = 0.8f;

    // Music is streamed from disk; short effects are loaded into memory
    private final Music backgroundMusic;
    private final Sound collectSound;
    private final Sound sneezeSound;
    private final Sound winSound;
    private final Sound loseSound;

    public GameAudio() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(MUSIC_VOLUME);

        collectSound = Gdx.audio.newSound(Gdx.files.internal("collect.mp3"));
        sneezeSound = Gdx.audio.newSound(Gdx.files.internal("sneeze.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("win.mp3"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("lose.mp3"));
    }

    public void startMusic() {
        backgroundMusic.play();
    }

    public void pauseMusic() {
        backgroundMusic.pause();
    }

    public void playCollect() {
        collectSound.play(EFFECT_VOLUME);
    }

    public void playSneeze() {
        sneezeSound.play(EFFECT_VOLUME);
    }

    public void playWin() {
        winSound.play(EFFECT_VOLUME);
    }

    public void playLose() {
        loseSound.play(EFFECT_VOLUME);
    }

    public void dispose() {
        backgroundMusic.dispose();
        collectSound.dispose();
        sneezeSound.dispose();
        winSound.dispose();
        loseSound.dispose();
    }
}
