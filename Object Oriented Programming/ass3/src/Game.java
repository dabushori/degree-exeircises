// ID - 212945760

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The Game class, which will make the game creating easier.
 *
 * @author Ori Dabush
 */
public class Game {

    // Sizes of the screen.
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Size of the borders.
    private static final int BORDER_SIZE = 10;
    // Color of the borders
    private static final Color BORDER_COLOR = Color.GRAY;

    // Sizes and start location of the paddle.
    private static final Color PADDLE_COLOR = Color.YELLOW;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final Point PADDLE_START_LOCATION = new Point(350, HEIGHT - (BORDER_SIZE + PADDLE_HEIGHT));

    // Sizes of the blocks.
    private static final int BLOCK_WIDTH = (WIDTH - 2 * BORDER_SIZE) / 15;
    private static final int BLOCK_HEIGHT = (HEIGHT - 2 * BORDER_SIZE) / 20;

    // parameters of the ball.
    private static final Point BALL_START_LOCATION = new Point(400, 555);
    private static final int BALL_RADIUS = 5;
    private static final int BALL_SPEED = 5;
    private static final Color BALL_COLOR = Color.WHITE;
    // Number of balls.
    private static final int NUM_OF_BALLS = 2;

    // The background color.
    private static final Color BACKGROUND_COLOR = Color.BLUE;

    // The color array for the block's lines.
    private static final Color[] COLORS = {Color.GRAY, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK, Color.GREEN};

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    /**
     * A constructor for the game class.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    /**
     * A method to add a collidable object to the game.
     *
     * @param c the collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * A method to add a sprite object to the game.
     *
     * @param s the sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * A method to get a keyboard sensor from the current object's gui.
     *
     * @return a keyboard sensor.
     */
    public KeyboardSensor getKeyboardSensor() {
        return this.gui.getKeyboardSensor();
    }

    /**
     * An access method to the current object's environment.
     *
     * @return the current object's environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * A method to create the blocks in the wanted pattern for ass3, using the factory f.
     *
     * @param f the used factory.
     */
    private void createBlocks(Factory f) {
        int startX = 3, colorIndex = 0;
        for (int y = 3; y < 9; y++) {
            f.createBlockLine(startX, 15, y, COLORS[colorIndex]);
            colorIndex++;
            startX++;
        }
    }

    /**
     * A method to color the background.
     *
     * @param d is the GUI's DrawSurface.
     */
    private static void colorBackground(DrawSurface d) {
        d.setColor(BACKGROUND_COLOR);
        d.fillRectangle(0, 0, WIDTH, HEIGHT);
    }

    /**
     * A method to initialize the game (creating the game and its objects).
     */
    public void initialize() {
        // Creating the GUI
        this.gui = new GUI("Arkanoid", WIDTH, HEIGHT);

        // Creating a factory object
        Factory f = new Factory(this);

        // Creating the paddle
        f.createPaddle(PADDLE_START_LOCATION, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        // Creating the balls
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            f.createBall(BALL_START_LOCATION, BALL_RADIUS, BALL_COLOR, BALL_SPEED);
        }

        // Creating the borders
        f.createBorders(BORDER_COLOR);

        // Creating the blocks
        createBlocks(f);
    }

    /**
     * A method to run the animation loop.
     */
    public void run() {
        GUI g = this.gui;
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = g.getDrawSurface();
            colorBackground(d);
            this.sprites.drawAllOn(d);
            g.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}