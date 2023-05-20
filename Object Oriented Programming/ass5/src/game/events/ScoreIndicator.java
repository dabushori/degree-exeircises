// ID - 212945760

package game.events;

import biuoop.DrawSurface;
import extras.Counter;
import game.objects.Sprite;
import game.operation.Game;
import geometry.Rectangle;

import java.awt.Color;

/**
 * This is the ScoreIndicator class, which will be used to show the player's score through the game.
 *
 * @author Ori Dabush.
 */
public class ScoreIndicator implements Sprite {
    private Rectangle shape;
    private Counter score;

    /**
     * A constructor for the ScoreIndicator class.
     *
     * @param shape        the rectangle which the score will be viewed in.
     * @param scoreCounter the counter of the score of the game.
     */
    public ScoreIndicator(Rectangle shape, Counter scoreCounter) {
        this.shape = shape;
        this.score = scoreCounter;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        Rectangle rect = this.shape;
        d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
        d.setColor(Color.BLACK);
        d.drawText(350, 19, "Score: " + this.score.getValue(), 20);
    }

    @Override
    public void timePassed() {
        // nothing here
    }

    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}