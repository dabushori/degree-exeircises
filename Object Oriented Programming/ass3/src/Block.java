// ID - 212945760

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The block class, which creates blocks that the ball will collide with.
 *
 * @author Ori Dabush
 */
public class Block implements Collidable, Sprite {

    /**
     * Epsilon is a const to compare between double numbers.
     */
    private static final double EPSILON = Math.pow(10, -8);

    /**
     * A method to compare between 2 double variables using epsilon const.
     *
     * @param d1 the first double variable.
     * @param d2 the second double variable.
     * @return true if they are equal, false otherwise.
     */
    private static boolean cmpDouble(double d1, double d2) {
        return Math.abs(d1 - d2) < EPSILON;
    }

    private Rectangle rectangle;
    private Color color;

    /**
     * A constructor to create a block object.
     *
     * @param rectangle the block's rectangle, the location the block will be in.
     * @param color     the block's color.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = new Rectangle(rectangle);
        this.color = color;
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
     * @param collisionPoint  the point where the collision had happened.
     * @param currentVelocity the velocity before the collision.
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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
        return currentVelocity;
    }

    /**
     * A method to add the block to the game.
     *
     * @param g the game.
     */
    @Override
    public void addToGame(Game g) {
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
}