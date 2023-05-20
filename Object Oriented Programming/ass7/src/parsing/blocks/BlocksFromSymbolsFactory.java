// ID - 212945760

package parsing.blocks;

import game.objects.Block;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the BlocksFromSymbolsFactory class which is in charge of creating the blocks of a level.
 *
 * @author Ori Dabush.
 */
public class BlocksFromSymbolsFactory {
    public static final String[] FIELDS = new String[]{
            "symbol",
            "height",
            "width",
            "fill",
            "stroke"
    };

    private static final String SYMBOL = FIELDS[0];
    private static final String HEIGHT = FIELDS[1];
    private static final String WIDTH = FIELDS[2];
    private static final String FILL = FIELDS[3];
    private static final String STROKE = FIELDS[4];

    private BlockParser parser;
    private List<String> blockDefinition;
    private List<String> blockSection;
    private int rowHeight;
    private int startX;
    private int startY;

    /**
     * A constructor for the BlocksFromSymbolsFactory class.
     *
     * @param parser          the block-parser of the current level.
     * @param blockDefinition the lines of the block-definition from the block's file.
     * @param blockSection    the block's section from the level's file.
     * @param rowHeight       the height of every row.
     * @param startX          the start-x.
     * @param startY          the start-y.
     */
    public BlocksFromSymbolsFactory(BlockParser parser, List<String> blockDefinition, List<String> blockSection,
                                    int rowHeight, int startX, int startY) {
        this.parser = parser;
        this.blockDefinition = blockDefinition;
        this.blockSection = blockSection;
        this.rowHeight = rowHeight;
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * A method to create a blocks from the block's section & file.
     *
     * @return the blocks list.
     */
    public List<Block> createBlocks() {
        parser.parse();
        parser.check();
        List<Block> blocks = new LinkedList<>();
        int y = this.startY;
        for (String line : this.blockSection) {
            createBlockLine(blocks, line, y);
            y += this.rowHeight;
        }
        return blocks;
    }

    /**
     * adding a line (given as a string) to a given list.
     *
     * @param list the list.
     * @param line the line.
     * @param y    the y-value of the line.
     */
    public void createBlockLine(List<Block> list, String line, int y) {
        int x = this.startX;
        for (int i = 0; i < line.length(); i++) {
            String symbol = "" + line.charAt(i);
            if (isSpaceSymbol(symbol)) {
                x += getSpaceWidth(symbol);
            } else if (isBlockSymbol(symbol)) {
                list.add(getBlock(symbol, x, y));
                x += this.parser.getBlocks().get(symbol).getWidth();
            } else {
                throw new RuntimeException("the symbol \'" + symbol + "\' does not exist.");
            }
        }
    }

    /**
     * A method to check if a string is a valid space symbol.
     *
     * @param s the string.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.parser.getSpacers().containsKey(s);
    }

    /**
     * A method to check if a string is a valid block symbol.
     *
     * @param s the string.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.parser.getBlocks().containsKey(s);
    }

    /**
     * A method to get a block according to the definitions associated with symbol s. The block will be
     * located at position (xpos, ypos).
     *
     * @param s    the block as a string.
     * @param xpos the x of the block.
     * @param ypos the y of the block.
     * @return a block according to the definitions associated with symbol s. The block will be located at
     * position (xpos, ypos).
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.parser.getBlocks().get(s).create(xpos, ypos);
    }

    /**
     * A method to get the width in pixels associated with the given spacer-symbol.
     *
     * @param s the spacer-symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.parser.getSpacers().get(s);
    }
}