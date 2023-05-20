// ID - 212945760

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The Paddle class, which creating the paddle of the game.
 *
 * @author Ori Dabush
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rectangle;
    private Color color;
    private KeyboardSensor keyboard;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int REGIONS = 5;

    private static final int PADDLE_SPEED = 4;

    /**
     * A constructor for the Paddle class.
     *
     * @param r the paddle's rectangle.
     * @param c the paddle's color.
     * @param k the keyboard sensor for the paddle.
     */
    public Paddle(Rectangle r, Color c, KeyboardSensor k) {
        this.rectangle = new Rectangle(r);
        this.color = c;
        this.keyboard = k;
    }

    /**
     * A method to check if the left arrow is pressed and move the paddle left if it is pressed.
     */
    public void moveLeft() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) && 0 < this.rectangle.getUpperLeft().getX()) {
            this.rectangle.moveRectangleHorizontal(-PADDLE_SPEED);
        }
    }

    /**
     * A method to check if the right arrow is pressed and move the paddle right if it is pressed.
     */
    public void moveRight() {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() < WIDTH) {
            this.rectangle.moveRectangleHorizontal(PADDLE_SPEED);
        }
    }

    /**
     * A method to notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        this.moveLeft();
        this.moveRight();
    }

    /**
     * A method to draw the paddle on a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.rectangle;
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
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
     * A method to find the region of the collision point with the paddle.
     *
     * @param p the collision point.
     * @return the number of the region (1 to 5).
     */
    private int findRegion(Point p) {
        int difference = (int) (p.getX() - this.rectangle.getUpperLeft().getX());
        int widthOfRegion = (int) this.rectangle.getWidth() / REGIONS;
        return (difference / widthOfRegion) + 1;
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
        Point beforeCollision = new Point(collisionPoint.getX() - currentVelocity.getDx(),
                collisionPoint.getY() - currentVelocity.getDy());
        Line trajectory = new Line(beforeCollision, collisionPoint);
        Point exactIntersectionPoint = trajectory.closestIntersectionToStartOfLine(this.rectangle);
        double speed =
                Math.round(Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2)));
        // Finding the collision point region.
        int region = findRegion(exactIntersectionPoint);
        if (region == 1) {
            currentVelocity = Velocity.fromAngleAndSpeed(300, speed);
        } else if (region == 2) {
            currentVelocity = Velocity.fromAngleAndSpeed(330, speed);
        } else if (region == 3) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (region == 4) {
            currentVelocity = Velocity.fromAngleAndSpeed(30, speed);

        } else if (region == 5) {
            currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
        }
        return currentVelocity;
    }

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
        return Math.abs(d1 - d2) <= EPSILON;
    }

    /**
     * A method to add this paddle to the game.
     *
     * @param g the game.
     */
    @Override
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}