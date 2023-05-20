// ID - 212945760

package game.levels.level1;

import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.Sprite;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the DirectHit class, which is level 1.
 *
 * @author Ori Dabush.
 */
public class DirectHit implements LevelInformation {
    private int numberOfBlocks;

    /**
     * A constructor for the DirectHit class.
     */
    public DirectHit() {
        this.numberOfBlocks = 0;
    }

    @Override
    public int numberOfBalls() {
        return 1;
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
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new DirectHitDesign();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new LinkedList<>();
        blocks.add(new Block(new Rectangle(350, 100, 100, 50), Color.RED));
        this.numberOfBlocks++;
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return numberOfBlocks;
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
        return Color.WHITE;
    }

    @Override
    public Color textColor() {
        return Color.WHITE;
    }
}