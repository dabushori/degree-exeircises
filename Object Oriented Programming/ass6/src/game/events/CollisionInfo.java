// ID - 212945760

package game.events;

import game.objects.Collidable;
import geometry.Point;

/**
 * The CollisionInfo class, which gives information about a collision - it tells where it happened (a point) and
 * what is the collidable object that the collision was with.
 *
 * @author Ori Dabush
 */
public class CollisionInfo {

    // The point where the collision happens (where the ball is supposed to move to).
    private Point collisionPoint;
    // The collidable object that the collision was with.
    private Collidable collisionObject;

    /**
     * A constructor to create a CollisionInfo object from a given point and collidable object.
     *
     * @param collisionPoint  the point where the collision had happened.
     * @param collisionObject the collidable object that the collision was with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * An access method to get the point at which the collision occurs.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * An access method to get the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}