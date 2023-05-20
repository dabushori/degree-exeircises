// ID - 212945760

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The MultipleFramesBouncingBallsAnimation, which will create bouncing balls like in the
 * MultipleBouncingBallsAnimation class, only in 2 frames.
 *
 * @author Ori Dabush
 */
public class MultipleFramesBouncingBallsAnimation {

    /**
     * constants for the size of the screen.
     */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * constants for the borders of the frames.
     */
    private static final Point TOP_LEFT_1 = new Point(50, 50);
    private static final Point BOTTOM_RIGHT_1 = new Point(500, 500);
    private static final Point TOP_LEFT_2 = new Point(450, 450);
    private static final Point BOTTOM_RIGHT_2 = new Point(600, 600);

    /**
     * The main method to create bouncing balls like in the MultipleBouncingBallsAnimation class, only in 2 frames.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        try {
            GUI gui = new GUI("MultipleFramesBouncingBallsAnimation", WIDTH, HEIGHT);
            Sleeper sleeper = new Sleeper();
            Frame frame1 = new Frame(TOP_LEFT_1, BOTTOM_RIGHT_1, Color.gray),
                    frame2 = new Frame(TOP_LEFT_2, BOTTOM_RIGHT_2, Color.yellow);
            Ball[] balls = new Ball[args.length];
            for (int i = 0; i < balls.length; i++) {
                int size = 0;
                size = (int) Double.parseDouble(args[i]);
                if (i < args.length / 2) {
                    // to avoid the case of being in both of the frames
                    do {
                        balls[i] = frame1.createBallInFrame(size);
                    } while (balls[i].isBallInFrame(frame2));
                } else {
                    balls[i] = frame2.createBallInFrame(size);
                }
            }
            while (true) {
                DrawSurface d = gui.getDrawSurface();
                frame1.drawFrame(d);
                frame2.drawFrame(d);
                for (Ball ball : balls) {
                    ball.getFrame().checkColor(ball);
                    ball.drawOn(d);
                    ball.moveOneStep();
                    ball.getFrame().adjustBallToFrame(ball);
                }
                sleeper.sleepFor(50);  // wait for 50 milliseconds.
                gui.show(d);
            }
        } catch (RuntimeException e) {
            System.out.println("invalid input");
        }
    }
}
