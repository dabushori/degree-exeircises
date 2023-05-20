// ID - 212945760

package game.animation;

import biuoop.DrawSurface;

/**
 * This is the Animation interface, which will be implemented by animations we want to run.
 *
 * @author Ori Dabush.
 */
public interface Animation {
    /**
     * A method to do one frame of the animation.
     *
     * @param d is the DrawSurface the animation is drawn on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * A method that checks if the animation should stop or not.
     *
     * @return true if the animation should stop, false otherwise.
     */
    boolean shouldStop();
}