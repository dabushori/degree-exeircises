// ID - 212945760

package game.levels.level3;

import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.Sprite;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This is the BlackAndWhite class, which is level 3.
 *
 * @author Ori Dabush.
 */
public class BlackAndWhite implements LevelInformation {
    private int numOfBlocks;

    private static final Color NEON_GREEN = new Color(25, 255, 25);
    private static final Color NEON_BLUE = new Color(77, 195, 255);

    private static Random rand = new Random();

    /**
     * A constructor for the BlackAndWhite class.
     */
    public BlackAndWhite() {
        this.numOfBlocks = 0;
    }

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return Velocity.fromNumberOfBalls(numberOfBalls());
    }

    @Override
    public int paddleSpeed() {
        return 6;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Black & White";
    }

    @Override
    public Sprite getBackground() {
        return new BlackAndWhiteDesign();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new LinkedList<>();
        int startX = 300, endX = 800, startY = 100, endY = 225, blockWidth = 50, blockHeight = 25;
        boolean flag = true;
        for (int y = startY; y < endY; y += blockHeight) {
            Color color = flag ? NEON_BLUE : NEON_GREEN;
            for (int x = startX; x < endX; x += blockWidth) {
                blocks.add(new Block(new Rectangle(x, y, blockWidth, blockHeight), color));
                numOfBlocks++;
            }
            flag = !flag;
            startX += blockWidth;
        }

//        boolean flag = true;
//        for (int i = startX; i < endX; i += blockWidth) {
//            for (int j = startY; j <= endY; j += blockHeight) {
//                if (flag) {
//                    if (rand.nextBoolean()) {
//                        blocks.add(new Block(new Rectangle(i, j, blockWidth, blockHeight), NEON_GREEN));
//                    } else {
//                        blocks.add(new Block(new Rectangle(i, j, blockWidth, blockHeight), NEON_BLUE));
//                    }
//                    numOfBlocks++;
//                }
//                flag = !flag;
//            }
//        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return numOfBlocks;
    }

    @Override
    public Color ballsColor() {
        return Color.WHITE;
    }

    @Override
    public Color ballsBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color paddleColor() {
        return NEON_GREEN;
    }

    @Override
    public Color textColor() {
        return Color.BLACK;
    }
}
