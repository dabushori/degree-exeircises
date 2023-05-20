// ID - 212945760

package game.objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.operation.Game;
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
    private KeyboardSensor keyboard;

    /**
     * A constructor for the game_objects.Paddle class.
     *
     * @param r the paddle's rectangle.
     * @param c the paddle's color.
     * @param k the keyboard sensor for the paddle.
     */
    public Paddle(Rectangle r, Color c, KeyboardSensor k) {
        this.rectangle = new Rectangle(r.getUpperLeft(), r.getWidth(), 0);
        this.drawnRectangle = new Rectangle(r);
        this.color = c;
        this.keyboard = k;
    }

    /**
     * A method to check if the left arrow is pressed and move the paddle left if it is pressed.
     */
    public void moveLeft() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                && Game.SIDE_BORDER_SIZE < this.rectangle.getUpperLeft().getX()) {
            this.rectangle.moveRectangleHorizontal(-Game.PADDLE_SPEED);
            this.drawnRectangle.moveRectangleHorizontal(-Game.PADDLE_SPEED);
        }
    }

    /**
     * A method to check if the right arrow is pressed and move the paddle right if it is pressed.
     */
    public void moveRight() {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()
                < Game.WIDTH - Game.SIDE_BORDER_SIZE) {
            this.rectangle.moveRectangleHorizontal(Game.PADDLE_SPEED);
            this.drawnRectangle.moveRectangleHorizontal(Game.PADDLE_SPEED);
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
        int widthOfRegion = (int) this.rectangle.getWidth() / Game.REGIONS;
        return (difference / widthOfRegion) + 1;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point beforeCollision = new Point(collisionPoint.getX() - currentVelocity.getDx(),
                collisionPoint.getY() - currentVelocity.getDy());
        Line trajectory = new Line(beforeCollision, collisionPoint);
        Point exactIntersectionPoint = trajectory.closestIntersectionToStartOfLine(this.rectangle);
        double speed =
                Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
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


    @Override
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}