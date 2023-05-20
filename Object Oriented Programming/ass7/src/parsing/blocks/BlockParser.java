// ID - 212945760

package parsing.blocks;

import game.objects.Fill;
import parsing.Parser;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is the BlockParser class, which is responsible for parsing the block's file.
 *
 * @author Ori Dabush.
 */
public class BlockParser {

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

    private List<String> blockDefinition;
    private Map<String, String> defaults;
    private Map<String, BlockDataUnit> blocks;
    private Map<String, Integer> spacers;

    /**
     * A constructor for the BlockParser class.
     *
     * @param blockDefinition the lines of the block's file.
     */
    public BlockParser(List<String> blockDefinition) {
        if (blockDefinition == null) {
            throw new RuntimeException("the BLOCK sections are not valid");
        }
        List<String> copy = new LinkedList<>(blockDefinition);
        for (String str : copy) {
            if (str.startsWith("#") || str.equals("")) {
                blockDefinition.remove(str);
            }
        }
        this.blockDefinition = blockDefinition;
        this.defaults = new HashMap<>();
        this.blocks = new HashMap<>();
        this.spacers = new HashMap<>();
    }

    /**
     * A method to get the default values of the block's file.
     *
     * @return the default values of the block's file.
     */
    public Map<String, String> getDefaults() {
        return this.defaults;
    }

    /**
     * A method to get the block's list of the file.
     *
     * @return the block's list of the file.
     */
    public Map<String, BlockDataUnit> getBlocks() {
        return this.blocks;
    }

    /**
     * A method to get the spacers list of the file.
     *
     * @return the spacers list of the file.
     */
    public Map<String, Integer> getSpacers() {
        return this.spacers;
    }

    /**
     * A method to check the validity of the data.
     */
    public void check() {
        for (String symbol : this.blocks.keySet()) {
            if (symbol.length() != 1) {
                throw new RuntimeException("symbol length != 1");
            }
            BlockDataUnit current = this.blocks.get(symbol);
            if (current == null) {
                throw new RuntimeException("block's data is not valid.");
            }
            current.check();
        }
        for (String symbol : this.spacers.keySet()) {
            if (symbol.length() != 1) {
                throw new RuntimeException("symbol length != 1");
            }
        }
    }

    /**
     * A method to parse the block's file.
     */
    public void parse() {
        for (String line : blockDefinition) {
            String name = getName(line);
            line = line.substring(line.indexOf(" ") + 1);
            switch (name) {
                case "default":
                    parseDefault(line);
                    break;
                case "bdef":
                    parseBlock(line);
                    break;
                case "sdef":
                    parseSpacer(line);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * A method to get the type of the line - default, bdef or sdef.
     *
     * @param line is the line.
     * @return the line's type.
     */
    public String getName(String line) {
        String name = line.substring(0, line.indexOf(" "));
        switch (name) {
            case "default":
            case "bdef":
            case "sdef":
                return name;
            default:
                break;
        }
        throw new RuntimeException("Lines name is not valid. Name provided - " + name);
    }

    /**
     * A method to get the field's value in a given line.
     *
     * @param line           the line.
     * @param field          the field.
     * @param getFromDefault true if the value can be a default value, false if it can't.
     * @return the field's value as a string.
     */
    public String getFieldValue(String line, String field, boolean getFromDefault) {
        if (line.contains(field)) {
            line = line.substring(line.indexOf(field));
            if (line.contains(" ")) {
                return line.substring(line.indexOf(":") + 1, line.indexOf(" "));
            }
            return line.substring(line.indexOf(":") + 1);
        }
        if (getFromDefault && this.defaults.containsKey(field)) {
            return this.defaults.get(field);
        }
        return null;
    }

    /**
     * A method to get the symbol out from a line.
     *
     * @param line the line.
     * @return the symbol, null if there isn't.
     */
    public String parseSymbol(String line) {
        return getFieldValue(line, SYMBOL, false);
    }

    /**
     * A method to get the height out from a line.
     *
     * @param line           the line.
     * @param getFromDefault true if the value can be a default value, false if it can't.
     * @return the height.
     */
    public Integer parseHeight(String line, boolean getFromDefault) {
        String val = getFieldValue(line, HEIGHT, getFromDefault);
        if (val == null) {
            return null;
        }
        return Integer.parseInt(val);
    }

    /**
     * A method to get the width out from a line.
     *
     * @param line           the line.
     * @param getFromDefault true if the value can be a default value, false if it can't.
     * @return the width.
     */
    public Integer parseWidth(String line, boolean getFromDefault) {
        String val = getFieldValue(line, WIDTH, getFromDefault);
        if (val == null) {
            return null;
        }
        return Integer.parseInt(val);
    }

    /**
     * A method to get the fill out of a line.
     *
     * @param line           the line.
     * @param getFromDefault true if the value can be a default value, false if it can't.
     * @return the fill.
     */
    public Fill parseFill(String line, boolean getFromDefault) {
        Fill fill = null;
        String str = getFieldValue(line, FILL, getFromDefault);
        if (str == null) {
            return null;
        }
        if (str.startsWith("color(")) {
            fill = new Fill(Parser.parseColor(str));
        } else if (str.startsWith("image(")) {
            str = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
            Image img = null;
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(str));
            } catch (Exception e) {
                throw new RuntimeException("can't open block's image");
            }
            fill = new Fill(img);
        } else {
            return null;
        }
        return fill;
    }

    /**
     * A method to get the stroke out of a line.
     *
     * @param line           the line.
     * @param getFromDefault true if the value can be a default value, false if it can't.
     * @return the stroke, null if there isn't.
     */
    public Color parseStroke(String line, boolean getFromDefault) {
        String val = getFieldValue(line, STROKE, getFromDefault);
        if (val == null) {
            return null;
        }
        return Parser.parseColor(getFieldValue(line, STROKE, getFromDefault));
    }

    /**
     * A method to parse a line of a block definition - bdef.
     *
     * @param line the line.
     */
    public void parseBlock(String line) {
        String symbol = parseSymbol(line);
        if (symbol == null) {
            throw new RuntimeException("field " + STROKE + " doesn't exist.");
        }

        Integer height = parseHeight(line, true);
        if (height == null) {
            throw new RuntimeException("field " + HEIGHT + " doesn't exist.");
        }

        Integer width = parseWidth(line, true);
        if (width == null) {
            throw new RuntimeException("field " + WIDTH + " doesn't exist.");
        }

        Fill fill = parseFill(line, true);
        if (fill == null) {
            throw new RuntimeException("field " + FILL + " doesn't exist.");
        }

        Color stroke = parseStroke(line, true);

        this.blocks.put(symbol, new BlockDataUnit(height, width, fill, stroke));
    }

    /**
     * A method to parse a line of a spacer definition - sdef.
     *
     * @param line the line.
     */
    public void parseSpacer(String line) {
        String symbol = parseSymbol(line);
        if (symbol == null) {
            throw new RuntimeException("field " + SYMBOL + " doesn't exist.");
        }

        Integer width = parseWidth(line, false);
        if (width == null) {
            throw new RuntimeException("field " + WIDTH + " doesn't exist.");
        }

        this.spacers.put(symbol, width);
    }

    /**
     * A method to parse a line of a default definition.
     *
     * @param line the line.
     */
    public void parseDefault(String line) {
        String height = getFieldValue(line, HEIGHT, false);
        if (height != null) {
            this.defaults.put(HEIGHT, height);
        }

        String width = getFieldValue(line, WIDTH, false);
        if (width != null) {
            this.defaults.put(WIDTH, width);
        }

        String fill = getFieldValue(line, FILL, false);
        if (fill != null) {
            this.defaults.put(FILL, fill);
        }

        String stroke = getFieldValue(line, STROKE, false);
        if (stroke != null) {
            this.defaults.put(STROKE, stroke);
        }
    }
}