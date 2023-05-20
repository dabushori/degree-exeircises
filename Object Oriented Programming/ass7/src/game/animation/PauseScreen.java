// ID - 212945760

package game.animation;

import biuoop.DrawSurface;
import game.events.SpriteCollection;

import java.awt.Color;

/**
 * This is the PauseScreen animation.
 *
 * @author Ori Dabush.
 */
public class PauseScreen implements Animation {
    private SpriteCollection sprites;
    private Color color;

    /**
     * A constructor for the PauseScreen class.
     *
     * @param sprites the game's objects.
     * @param c       the text color.
     */
    public PauseScreen(SpriteCollection sprites, Color c) {
        this.sprites = sprites;
        this.color = c;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(150, d.getHeight() / 2 + 25 - 30, 475, 32);

        d.setColor(color);
        d.drawText(150, d.getHeight() / 2 + 25, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
