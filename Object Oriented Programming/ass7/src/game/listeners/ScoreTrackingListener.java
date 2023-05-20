// ID - 212945760

package game.listeners;

import extras.Counter;
import game.objects.Ball;
import game.objects.Block;
import game.levels.GameLevel;

/**
 * This is the ScoreTrackingListener class, which will be used to count the player's score.
 *
 * @author Ori Dabush.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * A constructor for the ScoreTrackingListener class.
     *
     * @param scoreCounter the score's counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(GameLevel.BLOCK_DESTROYING_POINTS);
        beingHit.removeHitListener(this);
    }
}