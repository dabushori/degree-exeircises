// ID - 212945760

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The BouncingBallAnimation class, which gets 4 double numbers as command line arguments and
 * activates the drawAnimation method.
 *
 * @author Ori Dabush
 */
public class BouncingBallAnimation {

    /**
     * constants for the size of the screen.
     */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * constants for the screen borders.
     */
    private static final int TOP_BORDER = 0;
    private static final int BOTTOM_BORDER = HEIGHT;
    private static final int LEFT_BORDER = 0;
    private static final int RIGHT_BORDER = WIDTH;

    /**
     * A method that checks if the ball (all of it) is in the screen.
     *
     * @param ball the ball.
     * @return true if the ball is in the screen, false otherwise.
     */
    private static boolean isBallInScreen(Ball ball) {
        return ball.getX() > LEFT_BORDER + ball.getSize() && ball.getX() < RIGHT_BORDER - ball.getSize()
                && ball.getY() > TOP_BORDER + ball.getSize() && ball.getY() < BOTTOM_BORDER - ball.getSize();
    }

    /**
     * A method that makes a ball moving with the given velocity, starting in the given point.
     *
     * @param start the point where the ball starts moving from.
     * @param dx    the dx value of the ball's velocity.
     * @param dy    the dy value of the ball's velocity.
     */
    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("BouncingBallAnimation", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start.getX(), start.getY(), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        while (!isBallInScreen(ball)) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            if (ball.getX() <= LEFT_BORDER + ball.getSize() || ball.getX() >= RIGHT_BORDER - ball.getSize()) {
                Velocity current = ball.getVelocity();
                Velocity opposite = new Velocity(-current.getDx(), current.getDy());
                ball.setVelocity(opposite);
            }
            if (ball.getY() <= TOP_BORDER + ball.getSize() || ball.getY() >= BOTTOM_BORDER - ball.getSize()) {
                Velocity current = ball.getVelocity();
                Velocity opposite = new Velocity(current.getDx(), -current.getDy());
                ball.setVelocity(opposite);
            }
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

    /**
     * The main method which gets 4 double numbers as command line arguments and
     * activates the drawAnimation method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("invalid input!");
        } else {
            try {
                double dx = Double.parseDouble(args[2]);
                double dy = Double.parseDouble(args[3]);
                Point start = new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
                drawAnimation(start, dx, dy);
            } catch (Exception e) {
                System.out.println("invalid input!");
            }
        }
    }
}
