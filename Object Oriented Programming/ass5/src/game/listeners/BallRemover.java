// ID - 212945760

package game.listeners;

import extras.Counter;
import game.objects.Ball;
import game.objects.Block;
import game.operation.Game;

/**
 * This is the BallRemover class which will be used to remove balls and count the remaining balls.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter numOfBalls;

    /**
     * A constructor for the BallRemover class.
     * @param game the current game.
     * @param numOfBalls a counter for the balls in the current game.
     */
    public BallRemover(Game game, Counter numOfBalls) {
        this.game = game;
        this.numOfBalls = numOfBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.numOfBalls.decrease(1);
    }
}