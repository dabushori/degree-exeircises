// ID - 212945760

import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The ball class. A ball (actually, a circle) has size (radius), color, a location (a Point) and a velocity.
 *
 * @author Ori Dabush
 */
public class Ball {

    /**
     * A constant for the size that the velocity is equal from it and above (for the method createVelocity).
     */
    private static final int MAX_SIZE = 50;

    /**
     * A constant to calculate the velocity using the size.
     * dx = width / (VELOCITY_CALCULATOR * size)
     * dy = height / (VELOCITY_CALCULATOR * size)
     */
    private static final double VELOCITY_CALCULATOR = 4;

    private Point center;
    private int size;
    private Color color;
    private Velocity velocity;
    private Frame frame;

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
        this.frame = null;
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
        this.frame = null;
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
        this.frame = null;
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
     * A method that draws the ball on the given DrawSurface.
     *
     * @param surface is the draw surface the circle is going to be drown on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.size);
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
     * A method to get the frame that the ball is in.
     *
     * @return the frame the ball is in.
     */
    public Frame getFrame() {
        return this.frame;
    }

    /**
     * A method to change the ball's frame.
     *
     * @param f the desired frame.
     */
    public void setFrame(Frame f) {
        this.frame = f;
    }

    /**
     * A method that moves the ball one step by its velocity,
     * using the applyToPoint method on the ball's center point.
     */
    public void moveOneStep() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * A method to check if the ball is in a given frame.
     *
     * @param f the frame.
     * @return true if it is in the frame, false otherwise.
     */
    public boolean isBallInFrame(Frame f) {
        return f.getLeftBorder() <= this.getX() && this.getX() <= f.getRightBorder() && f.getTopBorder() <= this.getY()
                && this.getY() <= f.getBottomBorder();
    }

    /**
     * A method that creates a velocity for the ball using a specific frame, the VELOCITY_CALCULATOR
     * constant and the formula (dx = width / (VELOCITY_CALCULATOR * size, dy = height / (VELOCITY_CALCULATOR * size).
     *
     * @param f the specific frame.
     */
    public void createVelocity(Frame f) {
        if (this.size < MAX_SIZE) {
            this.setVelocity(f.getWidth() / (VELOCITY_CALCULATOR * this.size),
                    f.getHeight() / (VELOCITY_CALCULATOR * this.size));
        } else {
            this.setVelocity(f.getWidth() / (VELOCITY_CALCULATOR * MAX_SIZE),
                    f.getHeight() / (VELOCITY_CALCULATOR * MAX_SIZE));
        }
    }
}