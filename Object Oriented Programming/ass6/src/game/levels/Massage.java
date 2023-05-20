// ID - 212945760

package game.levels;

import biuoop.DrawSurface;
import game.objects.Sprite;
import geometry.Point;

import java.awt.Color;

/**
 * This is the Massage class, which describes a massage that is seen on the screen.
 */
public class Massage implements Sprite {
    private String massage;
    private Point location;
    private int size;
    private Color color;

    /**
     * A constructor for the Massage class.
     *
     * @param massage  the massage.
     * @param location the location of the massage.
     * @param size     the size of the massage.
     * @param color    the color of the massage.
     */
    public Massage(String massage, Point location, int size, Color color) {
        this.massage = massage;
        this.location = location;
        this.size = size;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.drawText((int) location.getX(), (int) location.getY(), massage, size);
    }

    @Override
    public void timePassed() {
        // do nothing
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
