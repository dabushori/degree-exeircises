// ID - 212945760

package game.objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.levels.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;

/**
 * The game_objects.Paddle class, which creating the paddle of the game.
 *
 * @author Ori Dabush
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rectangle;
    private Rectangle drawnRectangle;
    private Color color;
    private KeyboardSensor keyboardSensor;
    private int speed;

    /**
     * A constructor for the game_objects.Paddle class.
     *
     * @param r     the paddle's rectangle.
     * @param c     the paddle's color.
     * @param k     the keyboard sensor for the paddle.
     * @param speed the speed of the paddle.
     */
    public Paddle(Rectangle r, Color c, KeyboardSensor k, int speed) {
        this.rectangle = new Rectangle(r.getUpperLeft(), r.getWidth(), 0);
        this.drawnRectangle = new Rectangle(r);
        this.color = c;
        this.keyboardSensor = k;
        this.speed = speed;
    }

    /**
     * A method to check if the left arrow is pressed and move the paddle left if it is pressed.
     */
    public void moveLeft() {
        if (this.keyboardSensor.isPressed(KeyboardSensor.LEFT_KEY)
                && 0 < this.rectangle.getUpperLeft().getX()) {
            this.rectangle.moveRectangleHorizontal(-speed);
            this.drawnRectangle.moveRectangleHorizontal(-speed);
        }
    }

    /**
     * A method to check if the right arrow is pressed and move the paddle right if it is pressed.
     */
    public void moveRight() {
        if (this.keyboardSensor.isPressed(KeyboardSensor.RIGHT_KEY)
                && this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() < GameLevel.WIDTH) {
            this.rectangle.moveRectangleHorizontal(speed);
            this.drawnRectangle.moveRectangleHorizontal(speed);
        }
    }

    @Override
    public void timePassed() {
        this.moveLeft();
        this.moveRight();
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.drawnRectangle;
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }

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
        int widthOfRegion = (int) this.rectangle.getWidth() / GameLevel.REGIONS;
        return (difference / widthOfRegion) + 1;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point beforeCollision = new Point(collisionPoint.getX() - currentVelocity.getDx(),
                collisionPoint.getY() - currentVelocity.getDy());
        Line trajectory = new Line(beforeCollision, collisionPoint);
        Point exactIntersectionPoint = trajectory.closestIntersectionToStartOfLine(this.rectangle);
        double ballSpeed =
                Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        // Finding the collision point region.
        int region = findRegion(exactIntersectionPoint);
        if (region == 1) {
            currentVelocity = Velocity.fromAngleAndSpeed(300, ballSpeed);
        } else if (region == 2) {
            currentVelocity = Velocity.fromAngleAndSpeed(330, ballSpeed);
        } else if (region == 3) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (region == 4) {
            currentVelocity = Velocity.fromAngleAndSpeed(30, ballSpeed);

        } else if (region == 5) {
            currentVelocity = Velocity.fromAngleAndSpeed(60, ballSpeed);
        }
        return currentVelocity;
    }


    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}