// ID - 212945760

package game.listeners;

import game.objects.Ball;
import game.objects.Block;

/**
 * This is the HitListener interface, which will be used to receive notifications of hitting objects in the game.
 *
 * @author Ori Dabush.
 */
public interface HitListener {

    /**
     * A method that is operated when a hit notification is received.
     *
     * @param beingHit the block that is being hit.
     * @param hitter   the ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}