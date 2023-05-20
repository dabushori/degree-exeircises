// ID - 212945760

package parsing.blocks;

import game.objects.Block;
import game.objects.Fill;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * This is the BlockDataUnit, which is responsible of saving a block's data while parsing the levels.
 *
 * @author Ori Dabush.
 */
public class BlockDataUnit {
    private int height;
    private int width;
    private Fill fill;
    private Color stroke;

    /**
     * A constructor for the BlockDataUnit class.
     *
     * @param height the height of the block.
     * @param width  the width of the block.
     * @param fill   the fill of the block.
     * @param stroke the border color of the block.
     */
    public BlockDataUnit(int height, int width, Fill fill, Color stroke) {
        this.height = height;
        this.width = width;
        this.fill = fill;
        this.stroke = stroke;
    }

    /**
     * An access method to the block's height.
     *
     * @return the block's height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * An access method to the block's width.
     *
     * @return the block's width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * An access method to the block's fill.
     *
     * @return the block's fill.
     */
    public Fill getFill() {
        return fill;
    }

    /**
     * An access method to the block's stroke.
     *
     * @return the block's stroke.
     */
    public Color getStroke() {
        return stroke;
    }

    /**
     * A method to check the validity of the data.
     */
    public void check() {
        if (this.fill == null) {
            throw new RuntimeException("fill is invalid.");
        }
        if (this.stroke == null) {
            throw new RuntimeException("stroke is invalid.");
        }
    }

    /**
     * A method to create a block out of the data.
     *
     * @param x the x of the block.
     * @param y the y of the block.
     * @return the block that was create.
     */
    public Block create(int x, int y) {
        return new Block(new Rectangle(new Point(x, y), this.width, this.height), this.fill, this.stroke);
    }
}