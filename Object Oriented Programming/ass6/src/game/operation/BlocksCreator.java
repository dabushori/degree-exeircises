// ID - 212945760

package game.operation;

import game.objects.Block;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.List;
import java.util.LinkedList;

/**
 * This is the BlocksCreator, which will help creating blocks.
 *
 * @author Ori Dabush.
 */
public class BlocksCreator {
    private int blockWidth;
    private int blockHeight;

    /**
     * A constructor for the BlocksCreator class.
     *
     * @param blockWidth  the width of every blocks.
     * @param blockHeight the height of every block.
     */
    public BlocksCreator(int blockWidth, int blockHeight) {
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    /**
     * Assuming that the screen is a block's array, with 15 blocks (indexes 0-14) in a line and 20 block in a column
     * (indexes 0-19), this is a method to create a block in the location (x,y) of this array.
     *
     * @param x the block's location x value.
     * @param y the block's location y value.
     * @param c the block's color.
     * @return the block that was created.
     */
    public Block createBlock(int x, int y, Color c) {
        int upperLeftX = x * blockWidth;
        int upperLeftY = y * blockHeight;
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        return new Block(new Rectangle(upperLeft, blockWidth, blockHeight), c);
    }

    /**
     * Assuming that the screen is a block's array, with 800/blockWidth blocks in a line and 600/blockHeight blocks
     * in a column, this is a method to create a line of blocks in the lineIndex line of the array, starting from
     * startX and ending in endX.
     *
     * @param startX    the first block's x value.
     * @param endX      the last block's x value.
     * @param lineIndex the index of the line.
     * @param c         the blocks' color.
     * @return a list with all the blocks that were created.
     */
    public List<Block> createBlockLine(int startX, int endX, int lineIndex, Color c) {
        List<Block> blocks = new LinkedList<>();
        for (int i = startX; i < endX; i++) {
            blocks.add(createBlock(i, lineIndex, c));
        }
        return blocks;
    }

    /**
     * Assuming that the screen is a block's array, with 15 blocks (indexes 0-14) in a line and 20 block in a column
     * (indexes 0-19), this is a method to create a column of blocks in the columnIndex column of the array,
     * starting from startY and ending in endY.
     *
     * @param startY      the first block's y value.
     * @param endY        the last block's y value.
     * @param columnIndex the index of the column.
     * @param c           the blocks' color.
     * @return a list with all the blocks that were created.
     */
    public List<Block> createBlockColumn(int startY, int endY, int columnIndex, Color c) {
        List<Block> blocks = new LinkedList<>();
        for (int i = startY; i <= endY; i++) {
            blocks.add(createBlock(columnIndex, i, c));
        }
        return blocks;
    }
}