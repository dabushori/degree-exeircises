// ID - 212945760

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Random;

/**
 * The Frame class, which describing a frame. it will be used to create frames and draw stuff insitd of them.
 */
public class Frame {
    /**
     * constants to generate random colors - it's not until 256 to avoid very bright color's which won't be seen.
     */
    private static final int MAX_COLOR_R = 255;
    private static final int MAX_COLOR_G = 255;
    private static final int MAX_COLOR_B = 255;

    /**
     * The members of the class.
     */
    private Point topLeft;
    private Point bottomRight;
    private Color color;

    /**
     * Constructor that creates a frame from 2 points - top left and bottom right and color.
     *
     * @param topLeft     the top left point of the frame.
     * @param bottomRight the bottom right point of the frame.
     * @param color       the color of the frame.
     */
    public Frame(Point topLeft, Point bottomRight, Color color) {
        this.topLeft = new Point(topLeft);
        this.bottomRight = new Point(bottomRight);
        this.color = color;
    }

    /**
     * Constructor that creates a frame from 4 values that create 2 points - top left and bottom right, and color.
     *
     * @param x1    the topLeft point's x value.
     * @param y1    the topLeft point's y value.
     * @param x2    the bottomRight point's x value.
     * @param y2    the bottomRight point's y value.
     * @param color the frame's color.
     */
    public Frame(int x1, int y1, int x2, int y2, Color color) {
        this.topLeft = new Point(x1, y1);
        this.bottomRight = new Point(x2, y2);
        this.color = color;
    }

    /**
     * A method to access the frame's left border - the topLeft's x value.
     *
     * @return the left border of the frame.
     */
    public double getLeftBorder() {
        return this.topLeft.getX();
    }

    /**
     * A method to access the frame's right border - the bottomRight's x value.
     *
     * @return the right border of the frame.
     */
    public double getRightBorder() {
        return this.bottomRight.getX();
    }

    /**
     * A method to access the frame's top border - the topLeft's y value.
     *
     * @return the top border of the frame.
     */
    public double getTopBorder() {
        return this.topLeft.getY();
    }

    /**
     * A method to access the frame's bottom border - the bottomRight's y value.
     *
     * @return the bottom border of the frame.
     */
    public double getBottomBorder() {
        return this.bottomRight.getY();
    }

    /**
     * A method to get the width of the frame.
     *
     * @return the width of the frame.
     */
    public double getWidth() {
        return this.getRightBorder() - this.getLeftBorder();
    }

    /**
     * A method to get the height of the frame.
     *
     * @return the height of the frame.
     */
    public double getHeight() {
        return this.getBottomBorder() - this.getTopBorder();
    }

    /**
     * A method to draw the frame on a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    public void drawFrame(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.topLeft.getX(), (int) this.topLeft.getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * A method to get the frame's color.
     *
     * @return the frame's color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * A method to create a random color.
     *
     * @return the new color.
     */
    private static Color createRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(MAX_COLOR_R);
        int g = rand.nextInt(MAX_COLOR_G);
        int b = rand.nextInt(MAX_COLOR_B);
        return new Color(r, g, b);
    }

    /**
     * A method to create a ball in the frame, using a given size.
     *
     * @param size the given size.
     * @return the ball that has been created.
     * @throws RuntimeException if the ball's size is bigger than the frame.
     */
    public Ball createBallInFrame(int size) throws RuntimeException {
        if (size / 2 >= Math.max(this.getWidth(), this.getHeight())) {
            throw new RuntimeException("input is invalid. a ball is bigger than the frame's size.");
        }
        Random rand = new Random();
        int x = rand.nextInt((int) (this.getRightBorder() - 2 * size - this.getLeftBorder()))
                + (int) this.getLeftBorder() + size;
        int y = rand.nextInt((int) (this.getBottomBorder() - 2 * size - this.getTopBorder()))
                + (int) this.getTopBorder() + size;
        Point center = new Point(x, y);
        Ball ball = new Ball(center, size, createRandomColor());
        this.checkColor(ball);
        ball.setFrame(this);
        ball.createVelocity(this);
        return ball;
    }

    /**
     * A method to adjust the ball to the frame, which means to make the ball stay in the frame.
     *
     * @param ball the ball we want to adjust.
     */
    public void adjustBallToFrame(Ball ball) {
        if (ball.getX() <= this.getLeftBorder() + ball.getSize()
                || this.getRightBorder() - ball.getSize() <= ball.getX()) {
            Velocity current = ball.getVelocity();
            Velocity opposite = new Velocity(-current.getDx(), current.getDy());
            ball.setVelocity(opposite);
            ball.setColor(createRandomColor());
        }
        if (ball.getY() <= this.getTopBorder() + ball.getSize()
                || this.getBottomBorder() - ball.getSize() <= ball.getY()) {
            Velocity current = ball.getVelocity();
            Velocity opposite = new Velocity(current.getDx(), -current.getDy());
            ball.setVelocity(opposite);
            ball.setColor(createRandomColor());
        }
    }

    /**
     * A function to make sure that the color of a given ball is different than the frame's color.
     *
     * @param ball the given ball.
     */
    public void checkColor(Ball ball) {
        while (this.color.equals(ball.getColor())) {
            ball.setColor(createRandomColor());
        }
    }
}
