// ID - 212945760

package game.objects;

import biuoop.DrawSurface;
import game.levels.GameLevel;

/**
 * The gameEvents.Sprite interface, which will be implemented by our game game.objects that can be drawn to the screen
 * (and which is not just a background image).
 *
 * @author Ori Dabush
 */
public interface Sprite {

    /**
     * A method draw the sprite to the screen by a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    void drawOn(DrawSurface d);

    /**
     * A method to notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * A method to add the sprite to the game.
     *
     * @param g the game.
     */
    void addToGame(GameLevel g);
}