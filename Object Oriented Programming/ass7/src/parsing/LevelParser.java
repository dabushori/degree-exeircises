// ID - 212945760

package parsing;

import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.Sprite;
import geometry.Velocity;
import parsing.blocks.BlockParser;
import parsing.blocks.BlocksFromSymbolsFactory;

import java.awt.Color;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the LevelParser class, which will parse the levels and turn them into LevelInformation objects.
 *
 * @author Ori Dabush.
 */
public class LevelParser {
    public static final String[] FIELDS = new String[]{
            "level_name",
            "ball_velocities",
            "background",
            "paddle_speed",
            "paddle_width",
            "block_definitions",
            "blocks_start_x",
            "blocks_start_y",
            "row_height",
            "num_blocks"
    };

    private static final String LEVEL_NAME = FIELDS[0];
    private static final String BALL_VELOCITIES = FIELDS[1];
    private static final String BACKGROUND = FIELDS[2];
    private static final String PADDLE_SPEED = FIELDS[3];
    private static final String PADDLE_WIDTH = FIELDS[4];
    private static final String BLOCK_DEFINITION = FIELDS[5];
    private static final String BLOCKS_START_X = FIELDS[6];
    private static final String BLOCKS_START_Y = FIELDS[7];
    private static final String ROW_HEIGHT = FIELDS[8];
    private static final String NUM_BLOCKS = FIELDS[9];

    private List<List<String>> levels;

    /**
     * A constructor for the LevelParser class.
     *
     * @param lines the levels' file lines.
     */
    public LevelParser(List<String> lines) {
        this.levels = Parser.getSections(lines, "LEVEL");
    }

    /**
     * A method to check the validity of a level section.
     *
     * @param section the level section.
     */
    public void check(List<String> section) {
        if (Parser.getSection(section, "BLOCKS") == null) {
            throw new RuntimeException("the blocks section doesn't exist");
        }
        checkFields(section, FIELDS);
    }

    /**
     * A method to check if all the fields of a given level section are exist.
     *
     * @param section the level section.
     * @param fields  the fields' names.
     */
    public void checkFields(List<String> section, String[] fields) {
        for (String field : fields) {
            if (getFieldValues(section, field) == null) {
                throw new RuntimeException("the field " + field + " is missing");
            }
        }
    }

    /**
     * A method to parse the levels from the file and turn them into a list of LevelInformation objects.
     *
     * @return a list of LevelInformation objects which are the parsed levels.
     */
    public List<LevelInformation> parse() {
        List<LevelInformation> inf = new LinkedList<>();
        for (List<String> level : this.levels) {

            Reader reader = null;
            String pathToBlockDefinition = getFieldValues(level, BLOCK_DEFINITION);
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(pathToBlockDefinition);
            try {
                reader = new InputStreamReader(is);
            } catch (Exception e) {
                throw new RuntimeException("cant open blocks file");
            }

            check(level);

            List<String> blockDefinition = Parser.getLines(reader);
            BlockParser blockParser = new BlockParser(blockDefinition);

            List<String> blockSection = Parser.getSection(level, "BLOCKS");

            BlocksFromSymbolsFactory blocksFactory =
                    new BlocksFromSymbolsFactory(blockParser, blockDefinition, blockSection,
                            Integer.parseInt(getFieldValues(level, ROW_HEIGHT)),
                            Integer.parseInt(getFieldValues(level, BLOCKS_START_X)),
                            Integer.parseInt(getFieldValues(level, BLOCKS_START_Y)));

            LevelInformation current = new LevelInformation() {
                @Override
                public int numberOfBalls() {
                    return initialBallVelocities().size();
                }

                @Override
                public List<Velocity> initialBallVelocities() {
                    return parseVelocities(getFieldValues(level, BALL_VELOCITIES));
                }

                @Override
                public int paddleSpeed() {
                    return Integer.parseInt(getFieldValues(level, PADDLE_SPEED));
                }

                @Override
                public int paddleWidth() {
                    return Integer.parseInt(getFieldValues(level, PADDLE_WIDTH));
                }

                @Override
                public String levelName() {
                    return getFieldValues(level, LEVEL_NAME);
                }

                @Override
                public Sprite getBackground() {
                    return Parser.parseBackground(getFieldValues(level, BACKGROUND));
                }

                @Override
                public List<Block> blocks() {
                    return blocksFactory.createBlocks();
                }

                @Override
                public int numberOfBlocksToRemove() {
                    return Integer.parseInt(getFieldValues(level, NUM_BLOCKS));
                }

                @Override
                public Color ballsColor() {
                    return Color.BLACK;
                }

                @Override
                public Color ballsBorderColor() {
                    return Color.YELLOW;
                }

                @Override
                public Color paddleColor() {
                    return Color.YELLOW;
                }

                @Override
                public Color textColor() {
                    return Color.BLACK;
                }
            };

            inf.add(current);
        }
        return inf;
    }

    /**
     * A method to get the values of a field in a gicen level section.
     *
     * @param section   the level section.
     * @param fieldName the field's name.
     * @return the value of the field (as a string).
     */
    public String getFieldValues(List<String> section, String fieldName) {
        for (String line : section) {
            if (line.startsWith(fieldName + ":")) {
                return line.substring(line.indexOf(":") + 1);
            }
        }
        return null;
    }

    /**
     * A method to parse the velocities from a line of a,s to list of velocities.
     *
     * @param line the line.
     * @return a list of velocities with the velocities in them.
     */
    public List<Velocity> parseVelocities(String line) {
        List<Velocity> velocities = new LinkedList<>();

        String v = null;

        int index = line.indexOf(" ");
        if (index == -1) {
            velocities.add(parseVelocity(line));
        } else {
            v = line.substring(0, index);
            velocities.add(parseVelocity(v));
            line = line.substring(index + 1);

            while (true) {
                index = line.indexOf(" ");
                if (index == -1) {
                    velocities.add(parseVelocity(line));
                    break;
                }
                v = line.substring(0, index);
                velocities.add(parseVelocity(v));
                line = line.substring(index + 1);
            }
        }
        return velocities;
    }

    /**
     * A method to turn a a,s ("angle,speed") string into a velocity.
     *
     * @param v the a,s string.
     * @return the velocity that was parsed.
     */
    public Velocity parseVelocity(String v) {
        int index = v.indexOf(",");
        int angle = Integer.parseInt(v.substring(0, index));
        int speed = Integer.parseInt(v.substring(index + 1));
        return Velocity.fromAngleAndSpeed(angle, speed);
    }
}