// ID - 212945760

package game.levels.level4;

import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.Sprite;
import game.operation.BlocksCreator;
import geometry.Velocity;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Matrix class, which is level 4.
 *
 * @author Ori Dabush.
 */
public class Matrix implements LevelInformation {
    private int numOfBlocks;
    private MatrixDesign background;

    /**
     * A constructor for the Matrix class.
     */
    public Matrix() {
        this.numOfBlocks = 0;
        this.background = new MatrixDesign();
    }

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return Velocity.fromNumberOfBalls(numberOfBalls());
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "The Matrix";
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new LinkedList<>();

        BlocksCreator creator = new BlocksCreator(40, 25);
        for (int line = 3; line < 10; line++) {
            blocks.addAll(creator.createBlockLine(0, 20, line, Color.WHITE));
        }

//        BlocksCreator creator = new BlocksCreator(40, 20);
//        int missingBlocks = 5;
//        for (int line = 5; line < 15; line++) {
//            blocks.addAll(creator.createBlockLine(0, missingBlocks, line, Color.WHITE));
//            blocks.addAll(creator.createBlockLine(missingBlocks + 3, 20, line, Color.WHITE));
//            missingBlocks++;
//        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numOfBlocks;
    }

    @Override
    public Color ballsColor() {
        return Color.WHITE;
    }

    @Override
    public Color ballsBorderColor() {
        return Color.WHITE;
    }

    @Override
    public Color paddleColor() {
        return Color.WHITE;
    }

    @Override
    public Color textColor() {
        return Color.WHITE;
    }
}
