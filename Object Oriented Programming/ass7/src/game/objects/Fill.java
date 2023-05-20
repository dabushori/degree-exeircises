// ID - 212945760

package game.objects;

import biuoop.DrawSurface;
import game.levels.Background;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;

/**
 * This is the Fill class, which is the block's fill.
 *
 * @author Ori Dabush.
 */
public class Fill extends Background {

    private Rectangle shape;
    private Color color;
    private Image image;

    /**
     * A constructor for the fill class which gets a color.
     *
     * @param color the fill's color.
     */
    public Fill(Color color) {
        this.color = color;
    }

    /**
     * A constructor for the fill class which gets an image.
     *
     * @param image the fill's image.
     */
    public Fill(Image image) {
        this.image = image;
    }

    /**
     * A copy-constructor for the fill class which gets another Fill object.
     *
     * @param other the other fill.
     */
    public Fill(Fill other) {
        this.image = other.image;
        this.color = other.color;
    }

    /**
     * A method to set the shape of the Fill.
     *
     * @param r the new shape.
     */
    public void setShape(Rectangle r) {
        this.shape = r;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.color == null) {
            Point upperLeft = this.shape.getUpperLeft();
            d.drawImage((int) upperLeft.getX(), (int) upperLeft.getY(), this.image);
        }
        if (this.image == null) {
            Point upperLeft = shape.getUpperLeft();
            d.setColor(this.color);
            d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                    (int) shape.getWidth(), (int) shape.getHeight());
        }
    }
}