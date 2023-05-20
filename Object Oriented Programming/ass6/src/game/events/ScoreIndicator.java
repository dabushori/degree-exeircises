// ID - 212945760

package game.events;

import biuoop.DrawSurface;
import extras.Counter;
import game.objects.Sprite;
import game.levels.GameLevel;
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
    private Color color;

    /**
     * A constructor for the ScoreIndicator class.
     *
     * @param shape        the rectangle which the score will be viewed in.
     * @param scoreCounter the counter of the score of the game.
     * @param c is the score rectangle's color.
     */
    public ScoreIndicator(Rectangle shape, Counter scoreCounter, Color c) {
        this.shape = shape;
        this.score = scoreCounter;
        this.color = c;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        d.setColor(this.color);
        d.drawText(350, 19, "Score: " + this.score.getValue(), 20);
    }

    @Override
    public void timePassed() {
        // nothing here
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}