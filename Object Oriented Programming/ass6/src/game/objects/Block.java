// ID - 212945760

package game.objects;

import biuoop.DrawSurface;
import game.levels.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import game.listeners.HitListener;
import game.listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The block class, which creates blocks that the ball will collide with.
 *
 * @author Ori Dabush
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners;

    /**
     * A constructor to create a block object.
     *
     * @param rectangle the block's rectangle, the location the block will be in.
     * @param color     the block's color.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = new Rectangle(rectangle);
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * An access method to get the shape of the current block (in our case, rectangle).
     *
     * @return the "collision shape" of the current block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * A method to calculate the velocity after a collision.
     *
     * @param hitter          the ball that hit the current block.
     * @param collisionPoint  the point where the collision had happened.
     * @param currentVelocity the velocity before the collision.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null || currentVelocity == null) {
            return null;
        }
        Point upperRight = new Point(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth(),
                this.rectangle.getUpperLeft().getY());
        Point bottomLeft = new Point(this.rectangle.getUpperLeft().getX(),
                this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight());
        Point bottomRight = new Point(this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth(),
                this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight());
        Line upperEdge = new Line(this.rectangle.getUpperLeft(), upperRight);
        Line bottomEdge = new Line(bottomLeft, bottomRight);
        Line rightEdge = new Line(upperRight, bottomRight);
        Line leftEdge = new Line(this.rectangle.getUpperLeft(), bottomLeft);

        Point beforeCollision = new Point(collisionPoint.getX() - currentVelocity.getDx(),
                collisionPoint.getY() - currentVelocity.getDy());
        Line trajectory = new Line(beforeCollision, collisionPoint);

        Point intersectionWithRectangle = trajectory.closestIntersectionToStartOfLine(this.rectangle);

        if (upperEdge.isPointOnLine(intersectionWithRectangle) || bottomEdge.isPointOnLine(intersectionWithRectangle)) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if (rightEdge.isPointOnLine(intersectionWithRectangle) || leftEdge.isPointOnLine(intersectionWithRectangle)) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        this.notifyHit(hitter);

        return currentVelocity;
    }

    /**
     * A method to add the block to the game.
     *
     * @param g the game.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * A method to draw the block on a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.rectangle;
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }

    /**
     * A method to notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // currently does nothing.
    }

    /**
     * A method to remove a block from a game.
     *
     * @param gameLevel the game that the block will be removed from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * A method to notify the block's HitListeners about a hit event.
     *
     * @param hitter the ball that hit the current block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all game.listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}