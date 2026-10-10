
package com.university.allergicvacuum;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Handles the vacuum's sneeze when its allergy meter is full.
 * The vacuum shakes before sneezing and scattering collected items.
 */
public class SneezeEvent {

    private enum Phase {
        IDLE,
        WARNING,
        SNEEZING
    }

    private static final float WARNING_DURATION = 1.5f;
    private static final float SNEEZE_TEXT_DURATION = 0.8f;

    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private Phase phase = Phase.IDLE;
    private float timer;

    public SneezeEvent() {
        font = new BitmapFont();
        font.getData().setScale(1.4f);
    }

    /** Cancels any sneeze in progress. */
    public void reset() {
        phase = Phase.IDLE;
        timer = 0f;
    }

    /** Starts the warning shake. */
    public void start() {
        if (phase == Phase.IDLE) {
            phase = Phase.WARNING;
            timer = WARNING_DURATION;
        }
    }

    /**
     * Advances the sneeze animation.
     * @return true when the vacuum sneezes.
     */
    public boolean update(float delta) {
        if (phase == Phase.IDLE) {
            return false;
        }

        timer -= delta;

        if (timer > 0f) {
            return false;
        }

        if (phase == Phase.WARNING) {
            phase = Phase.SNEEZING;
            timer = SNEEZE_TEXT_DURATION;
            return true;
        }

        phase = Phase.IDLE;
        return false;
    }

    /** Returns true while the vacuum is shaking. */
    public boolean isWarning() {
        return phase == Phase.WARNING;
    }

    /** Draws the sneeze text above the vacuum. */
    public void render(SpriteBatch batch, Vacuum vacuum) {
        String text;

        if (phase == Phase.WARNING) {
            text = "Ah... ah...";
            font.setColor(Color.YELLOW);
        } else if (phase == Phase.SNEEZING) {
            text = "ACHOO!";
            font.setColor(Color.ORANGE);
        } else {
            return;
        }

        layout.setText(font, text);

        font.draw(
            batch,
            layout,
            vacuum.getX()
                + (vacuum.getWidth() - layout.width) / 2f,
            vacuum.getY() + vacuum.getHeight()
        );
    }

    public void dispose() {
        font.dispose();
    }
}
