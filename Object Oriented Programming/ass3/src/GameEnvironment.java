// ID - 212945760

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class, which contains a list of all the collidable objects in the game.
 *
 * @author Ori Dabush
 */
public class GameEnvironment {

    /**
     * The list of all the collidable objects in the game.
     */
    private List<Collidable> collidables;

    /**
     * A constructor for the GameEnvironment class, which creates a new empty ArrayList.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * A method to add the given collidable to the environment.
     *
     * @param c the given collidable.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end(). If this object will not collide with any of the
     * collidables in this collection, return null. Else, return the information about the closest collision
     * that is going to occur.
     *
     * @param trajectory the trajectory of the ball.
     * @return the information about the closest collision that is going to occur, or null if this object will
     * not collide with any of the collidables in this collection.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (trajectory == null || this.collidables.isEmpty()) {
            return null;
        }
        // The start of the trajectory line.
        Point start = trajectory.start();
        // The values of the closest CollisionInfo object to the start of the trajectory line.
        Collidable firstCollidable = this.collidables.get(0);
        Rectangle r = firstCollidable.getCollisionRectangle();
        Point closestCollision = trajectory.closestIntersectionToStartOfLine(r);
        // Checking if there's a closer collision.
        for (Collidable c : this.collidables) {
            r = c.getCollisionRectangle();
            Point closestIntersection = trajectory.closestIntersectionToStartOfLine(r);
            // Saving the details of the collision if it is closer to the start of the trajectory line.
            if (closestIntersection != null) {
                if (closestCollision == null) {
                    closestCollision = closestIntersection;
                    firstCollidable = c;
                } else if (start.distance(closestIntersection) < start.distance(closestCollision)) {
                    closestCollision = closestIntersection;
                    firstCollidable = c;
                }
            }
        }
        // Creating & returning the new CollisionInfo object.
        return new CollisionInfo(closestCollision, firstCollidable);
    }
}