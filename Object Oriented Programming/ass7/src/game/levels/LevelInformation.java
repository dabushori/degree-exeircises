// ID - 212945760

package game.levels;

import game.objects.Block;
import game.objects.Sprite;
import geometry.Velocity;

import java.awt.Color;
import java.util.List;

/**
 * This is the LevelInformation interface which describes a level in the game.
 *
 * @author Ori Dabush.
 */
public interface LevelInformation {
    /**
     * A method to get the number of balls in the level.
     *
     * @return the number of balls in the level.
     */
    int numberOfBalls();

    /**
     * A method to get the initial velocity of each ball.
     *
     * @return a list with the initial velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * A method to get the paddle's speed.
     *
     * @return the paddle's speed.
     */
    int paddleSpeed();

    /**
     * A method to get the paddle's width.
     *
     * @return the paddle's width.
     */
    int paddleWidth();

    /**
     * A method to get the level's name.
     *
     * @return the level's name.
     */
    String levelName();

    /**
     * A method to get the background of the level.
     *
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * A method to create the blocks of the level.
     *
     * @return a list with all the blocks.
     */
    List<Block> blocks();

    /**
     * A method to get the number of blocks that need to be removed.
     *
     * @return the number of blocks that need to be removed.
     */
    int numberOfBlocksToRemove();

    /**
     * A method to get the balls' color.
     *
     * @return the balls' color.
     */
    Color ballsColor();

    /**
     * A method to get the borders' color.
     *
     * @return the borders' color.
     */
    Color ballsBorderColor();

    /**
     * A method to get the paddle's color.
     *
     * @return the paddle's color.
     */
    Color paddleColor();

    /**
     * A method to get the text's color.
     *
     * @return the text's color.
     */
    Color textColor();
}