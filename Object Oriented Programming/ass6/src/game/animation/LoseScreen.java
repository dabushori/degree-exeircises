// ID - 212945760

package game.animation;

import biuoop.DrawSurface;
import game.events.SpriteCollection;

import java.awt.Color;

/**
 * This is the LoseScreen class.
 *
 * @author Ori Dabush.
 */
public class LoseScreen implements Animation {
    private boolean shouldStop;
    private SpriteCollection sprites;
    private Color textColor;
    private int score;

    /**
     * A constructor for the LoseScreen class.
     *
     * @param s     the game's objects.
     * @param color the text color.
     * @param score the player's score.
     */
    public LoseScreen(SpriteCollection s, Color color, int score) {
        shouldStop = false;
        sprites = s;
        textColor = color;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        sprites.drawAllOn(d);

        int numberLength = (int) Math.floor(Math.log10(score)) + 1, digitWidth = 18;

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(150, d.getHeight() / 2 + 25 - 30, 385 + numberLength * digitWidth, 32);

        d.setColor(textColor);
        d.drawText(150, d.getHeight() / 2 + 25, "Game Over. Your score is " + score + ".", 32);
    }

    @Override
    public boolean shouldStop() {
        return shouldStop;
    }
}
