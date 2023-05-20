// ID - 212945760

package game.objects;

import game.levels.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The Collidable interface will be used by things that can be collided with.
 *
 * @author Ori Dabush
 */
public interface Collidable {

    /**
     * An access method to get the shape of the collidable object (in our case, rectangle).
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * A method to calculate the velocity after a collision.
     *
     * @param hitter          the ball that hit the collidable object.
     * @param collisionPoint  the point where the collision had happened.
     * @param currentVelocity the velocity before the collision.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * A method to add the collidable into the game.
     *
     * @param g the game.
     */
    void addToGame(GameLevel g);
}