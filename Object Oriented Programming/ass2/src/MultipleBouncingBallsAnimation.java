// ID - 212945760

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * The MultipleBouncingBallsAnimation class, which will create an animation with the ball's size's from the command
 * line. Each ball will start in a random location on the screen. Each ball will start with a different speed -
 * we want larger balls to be slower (but balls above size 50 can all have the same slow speed).
 * Each ball will change direction when hitting the window border.
 *
 * @author Ori Dabush
 */
public class MultipleBouncingBallsAnimation {

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
     * A constant to calculate the velocity using the size.
     * dx = width / (VELOCITY_CALCULATOR * size)
     * dy = height / (VELOCITY_CALCULATOR * size)
     */
    private static final double VELOCITY_CALCULATOR = 4;

    /**
     * A constant for the size that the velocity is equal from it and above.
     */
    private static final int MAX_SIZE = 50;

    /**
     * constants to generate random colors - it's not until 256 to avoid very bright color's which won't be seen.
     */
    private static final int MAX_COLOR_R = 255;
    private static final int MAX_COLOR_G = 255;
    private static final int MAX_COLOR_B = 255;

    /**
     * A method to create velocity using ball's size and the VELOCITY_CALCULATOR and the formula.
     *
     * @param ball is the ball whose velocity is being calculated.
     */
    private static void createVelocity(Ball ball) {
        if (ball.getSize() < MAX_SIZE) {
            ball.setVelocity(WIDTH / (VELOCITY_CALCULATOR * ball.getSize()),
                    HEIGHT / (VELOCITY_CALCULATOR * ball.getSize()));
        } else {
            ball.setVelocity(WIDTH / (VELOCITY_CALCULATOR * MAX_SIZE),
                    HEIGHT / (VELOCITY_CALCULATOR * MAX_SIZE));
        }
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
     * A method to create a ball. the method creates a random center point and color, using the given size
     * and calculates the velocity of the new ball.
     *
     * @param size the ball's radius.
     * @return the new ball.
     */
    private static Ball createBall(int size) {
        if (size < 0) {
            throw new RuntimeException("the size can't be negative!");
        }
        Random rand = new Random();
        //random point
        int x = rand.nextInt(WIDTH - 2 * size) + size;
        int y = rand.nextInt(HEIGHT - 2 * size) + size;
        Point center = new Point(x, y);
        //random color
        Color color = createRandomColor();
        Ball ball = new Ball(center, size, color);
        createVelocity(ball);
        return ball;
    }

    /**
     * A method that checks if the ball is in the screen, and if it touches the borders it makes
     * it change its direction.
     *
     * @param ball the ball.
     */
    private static void adjustBallToScreen(Ball ball) {
        if (ball.getX() <= LEFT_BORDER + ball.getSize() || ball.getX() >= RIGHT_BORDER - ball.getSize()) {
            Velocity current = ball.getVelocity();
            Velocity opposite = new Velocity(-current.getDx(), current.getDy());
            ball.setVelocity(opposite);
            ball.setColor(createRandomColor());
        }
        if (ball.getY() <= TOP_BORDER + ball.getSize() || ball.getY() >= BOTTOM_BORDER - ball.getSize()) {
            Velocity current = ball.getVelocity();
            Velocity opposite = new Velocity(current.getDx(), -current.getDy());
            ball.setVelocity(opposite);
            ball.setColor(createRandomColor());
        }
    }

    /**
     * The main method, which will create an animation with the ball's size's from the command
     * * line. Each ball will start in a random location on the screen. Each ball will start with a different speed -
     * * we want larger balls to be slower (but balls above size 50 can all have the same slow speed).
     * * Each ball will change direction when hitting the window border.
     *
     * @param args the command line arguments, which are the sizes of the balls.
     */
    public static void main(String[] args) {
        try {
            // creating the ball array
            Ball[] ballsArray = new Ball[args.length];
            for (int i = 0; i < args.length; i++) {
                int size;
                //checking that sizes between 0 and 1 won't become 0 size.
                if (Double.parseDouble(args[i]) < 1.0) {
                    size = 1;
                } else {
                    size = (int) Double.parseDouble(args[i]);
                }
                ballsArray[i] = createBall(size);
            }
            GUI gui = new GUI("MultipleBouncingBallsAnimation", WIDTH, HEIGHT);
            Sleeper sleeper = new Sleeper();
            // moving the balls
            while (true) {
                DrawSurface d = gui.getDrawSurface();
                for (Ball ball : ballsArray) {
                    ball.drawOn(d);
                    ball.moveOneStep();
                    adjustBallToScreen(ball);
                }
                sleeper.sleepFor(50);  // wait for 50 milliseconds.
                gui.show(d);
            }
        } catch (RuntimeException e) {
            System.out.println("invalid input!");
        }
    }
}
