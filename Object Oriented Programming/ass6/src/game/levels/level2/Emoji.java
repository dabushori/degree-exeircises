// ID - 212945760

package game.levels.level2;

import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.Sprite;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Emoji class, which is level 2.
 *
 * @author Ori Dabush.
 */
public class Emoji implements LevelInformation {
    private int blocks;

    /**
     * A constructor for the Emoji class.
     */
    public Emoji() {
        this.blocks = 0;
    }

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return Velocity.fromNumberOfBalls(numberOfBalls());
    }

    @Override
    public int paddleSpeed() {
        return 1;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Smile";
    }

    @Override
    public Sprite getBackground() {
        return new EmojiDesign();
    }

    private Color[] rainbow = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
            new Color(53, 170, 234), new Color(148, 0, 211)};

    /**
     * A method to create a rainbow design, but I noticed that I need to copy your design AFTER I made it...
     *
     * @return a 2D array that describes colors of a rainbow.
     */
    private Color[][] createColorsArray() {
        Color[][] colors = new Color[13][26];
        for (int i = 0; i < 6; i++) {
            for (int j = 10; j < 16; j++) {
                colors[i][j] = rainbow[i];
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 7; j < 10; j++) {
                colors[i + 1][j] = rainbow[i];
            }
            for (int j = 16; j < 19; j++) {
                colors[i + 1][j] = rainbow[i];
            }
        }
        for (int j = 0; j < 6; j++) {
            for (int i = 9; i < 13; i++) {
                colors[i][j] = rainbow[j];
                colors[i][25 - j] = rainbow[j];
            }
        }
        for (int j = 0; j < 6; j++) {
            for (int i = 7; i < 9; i++) {
                colors[i][j + 1] = rainbow[j];
                colors[i][24 - j] = rainbow[j];
            }
        }
        for (int j = 1; j < 5; j++) {
            int i = 6;
            colors[i][j] = rainbow[j - 1];
            colors[i][25 - j] = rainbow[j - 1];
        }
        colors[6][6] = rainbow[4];
        colors[6][5] = rainbow[4];
        colors[6][19] = rainbow[4];
        colors[6][20] = rainbow[4];
        for (int i = 2; i < 6; i++) {
            for (int j = 5; j < 7; j++) {
                colors[i][j] = rainbow[i - 2];
                colors[i][25 - j] = rainbow[i - 2];
            }
        }
        colors[5][2] = rainbow[0];
        colors[4][3] = rainbow[0];
        colors[3][4] = rainbow[0];
        colors[5][23] = rainbow[0];
        colors[4][22] = rainbow[0];
        colors[3][21] = rainbow[0];
        colors[5][3] = rainbow[1];
        colors[4][4] = rainbow[1];
        colors[5][22] = rainbow[1];
        colors[4][21] = rainbow[1];
        colors[5][4] = rainbow[2];
        colors[5][21] = rainbow[2];
        return colors;
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new LinkedList<>();
        Color[][] colors = createColorsArray();
        int blockWidth = 800 / 32, blockHeight = 20;
        int topY = 50;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                if (colors[i][j] != null) {
                    blocks.add(new Block(new Rectangle((j + 3) * blockWidth, topY + i * blockHeight,
                            blockWidth, blockHeight), colors[i][j]));
                }
            }
        }
        return blocks;

//        List<Block> blocksList = new LinkedList<>();
//        int blockWidth = 800 / 16, blockHeight = 20, y = 200;
//        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
//                new Color(128, 0, 128), new Color(255, 0, 255)};
//        for (int x = 0; x < 800; x += blockWidth) {
//            blocksList.add(new Block(new Rectangle(x, y, blockWidth, blockHeight), colors[x / 100]));
//        }
//        return blocksList;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks;
    }

    @Override
    public Color ballsColor() {
        return Color.BLACK;
    }

    @Override
    public Color ballsBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color paddleColor() {
        return Color.BLACK;
    }

    @Override
    public Color textColor() {
        return Color.BLACK;
    }
}