// ID - 212945760

package game.listeners;

import game.objects.Ball;
import game.objects.Block;
import game.operation.Game;
import extras.Counter;

/**
 * This is the BlockRemover class, which is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * A constructor for the BlockRemover class.
     *
     * @param game            the current game.
     * @param remainingBlocks a counter for the remaining blocks.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * A method to get the counter of the remaining blocks.
     *
     * @return the counter of the remaining blocks.
     */
    public Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }

    /**
     * A method to remove blocks that were hit from the game.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}