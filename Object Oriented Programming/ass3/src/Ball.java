// ID - 212945760

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The ball class. A ball (actually, a circle) has size (radius), color, a location (a Point) and a velocity.
 *
 * @author Ori Dabush
 */
public class Ball implements Sprite {

    private Point center;
    private int size;
    private Color color;
    private Velocity velocity;
    private GameEnvironment env;

    /**
     * A constructor that creates a ball from a center point, radius and color.
     *
     * @param center the center point.
     * @param r      the radius.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.center = new Point(center);
        this.size = r;
        this.color = color;
        // in case that there's no need in velocity.
        this.velocity = new Velocity(0, 0);
        this.env = null;
    }

    /**
     * A constructor that creates a ball from x and y values, radius and color.
     *
     * @param x     the x value of the ball's location.
     * @param y     the y value of the ball's location.
     * @param r     the radius of the ball.
     * @param color the ball's color.
     */
    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.size = r;
        this.color = color;
        // in case that there's no need in velocity.
        this.velocity = new Velocity(0, 0);
        this.env = null;
    }

    /**
     * A constructor that creates a ball from double x and y values, radius and color.
     *
     * @param x     the x value of the ball's location.
     * @param y     the y value of the ball's location.
     * @param r     the radius of the ball.
     * @param color the ball's color.
     */
    public Ball(double x, double y, int r, Color color) {
        this.center = new Point(x, y);
        this.size = r;
        this.color = color;
        // in case that there's no need in velocity.
        this.velocity = new Velocity(0, 0);
        this.env = null;
    }

    /**
     * A method that gives access to the ball's location x value.
     *
     * @return the ball's location x value.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * A method that gives access to the ball's location y value.
     *
     * @return the ball's location y value.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * A method that gives access to the ball's size.
     *
     * @return the ball's size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * A method that gives access to the ball's color.
     *
     * @return the ball's color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * A method to set a new color to the ball.
     *
     * @param c the new color.
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * A method that sets the velocity value of the current ball to the given velocity.
     *
     * @param v the given velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v);
    }

    /**
     * A method that sets the velocity value of the current ball to the velocity made by the given dx, dy values.
     *
     * @param dx the dx value of the velocity.
     * @param dy the dy value of the velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * An accessor method to get the velocity value of the current ball.
     *
     * @return the velocity value of the current ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * A method that moves the ball one step by its velocity, using the applyToPoint method on the ball's
     * center point. The method will change the velocity depending on the GameEnvironment.
     */
    public void moveOneStep() {
        Point locationWithoutCollision = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(this.center, locationWithoutCollision);
        CollisionInfo info = this.env.getClosestCollision(trajectory);
        // The collisionPoint is a null when there's no collision.
        if (info == null || info.collisionPoint() == null) {
            this.center = locationWithoutCollision;
        } else {
            // change the velocity
            this.velocity = info.collisionObject().hit(locationWithoutCollision, this.velocity);
            // get the ball "almost" hitting the collidable
            Line toCollisionPoint = new Line(this.center, info.collisionPoint());
            this.center = toCollisionPoint.getEndCloser();
        }
    }

    /**
     * An access method to the game environment of the current ball.
     *
     * @return the game environment of the current ball.
     */
    public GameEnvironment getEnv() {
        return this.env;
    }

    /**
     * A method to set the game environment of the current ball.
     *
     * @param e the given game environment.
     */
    public void setEnv(GameEnvironment e) {
        this.env = e;
    }

    // Sprite methods.

    /**
     * A method that draws the ball on the given DrawSurface.
     *
     * @param surface is the draw surface the circle is going to be drown on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }

    /**
     * A method to notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * A method to add the ball to the game.
     *
     * @param g the game.
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}