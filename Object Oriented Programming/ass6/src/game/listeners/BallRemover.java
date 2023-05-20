// ID - 212945760

package game.listeners;

import extras.Counter;
import game.objects.Ball;
import game.objects.Block;
import game.levels.GameLevel;

/**
 * This is the BallRemover class which will be used to remove balls and count the remaining balls.
 *
 * @author Ori Dabush.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter numOfBalls;

    /**
     * A constructor for the BallRemover class.
     * @param gameLevel the current game.
     * @param numOfBalls a counter for the balls in the current game.
     */
    public BallRemover(GameLevel gameLevel, Counter numOfBalls) {
        this.gameLevel = gameLevel;
        this.numOfBalls = numOfBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.numOfBalls.decrease(1);
    }
}