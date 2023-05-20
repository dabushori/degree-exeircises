// ID - 212945760

package game.animation;

import biuoop.DrawSurface;
import game.events.SpriteCollection;

import java.awt.Color;

/**
 * This is the CountdownAnimation class. The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show a countdown from countFrom back to 1,
 * where each number will appear on the screen for (numOfSeconds / countFrom) seconds, before it is
 * replaced with the next one.
 *
 * @author Ori Dabush.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int counter;
    private SpriteCollection gameScreen;
    private boolean countEnded;
    private int frameNum;
    private Color color;

    /**
     * A constructor for the CountdownAnimation class.
     *
     * @param numOfSeconds the number of seconds the count will last.
     * @param countFrom    the number that the count starts from.
     * @param gameScreen   the game's screen.
     * @param c            the color of the text.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen, Color c) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.counter = countFrom;
        this.gameScreen = gameScreen;
        this.countEnded = false;
        this.frameNum = 1;
        this.color = c;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);

        if (this.counter > 0) {
            d.setColor(Color.LIGHT_GRAY);
            d.fillRectangle(200, d.getHeight() / 2 + 25 - 30, 375, 32);

            d.setColor(color);
            d.drawText(200, d.getHeight() / 2 + 25, "Game will be started in " + this.counter + "...", 32);
            this.frameNum++;
            if (this.frameNum == 60 * numOfSeconds / countFrom) {
                this.counter--;
                this.frameNum = 1;
            }
        } else {
            this.countEnded = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.countEnded;
    }
}