// ID - 212945760

package game.operation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import extras.Counter;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.ScoreTrackingListener;
import game.objects.Collidable;
import game.objects.Sprite;
import game.events.GameEnvironment;
import game.events.SpriteCollection;

import geometry.Point;

import java.awt.Color;

/**
 * The gameOperation.Game class, which will make the game creating easier.
 *
 * @author Ori Dabush
 */
public class Game {

    // Sizes of the screen.
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int SIDE_BORDER_SIZE = 10;
    public static final int TOP_BORDER_SIZE = 10;

    public static final int SCORE_WIDTH = WIDTH;
    public static final int SCORE_HEIGHT = 20;

    // Sizes of the blocks.
    public static final int BLOCK_WIDTH = (WIDTH - 2 * SIDE_BORDER_SIZE) / 15;
    public static final int BLOCK_HEIGHT = (HEIGHT - TOP_BORDER_SIZE - SCORE_HEIGHT) / 20;

    // Color of the borders
    public static final Color BORDER_COLOR = Color.GRAY;

    // Sizes and start location of the paddle.
    public static final Color PADDLE_COLOR = Color.YELLOW;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 5;
    public static final Point PADDLE_START_LOCATION = new Point(350, HEIGHT - TOP_BORDER_SIZE - PADDLE_HEIGHT);
    public static final int PADDLE_SPEED = 6;

    // parameters of the ball.
    public static final Point BALL_START_LOCATION = new Point(400, 555);
    public static final int BALL_RADIUS = 5;
    public static final int BALL_SPEED = 5;
    public static final Color BALL_COLOR = Color.WHITE;
    // Number of balls.
    public static final int NUM_OF_BALLS = 3;

    // The background color.
    public static final Color BACKGROUND_COLOR = new Color(0, 153, 230);

    // The color array for the block's lines.
    public static final Color[] COLORS = {Color.GRAY, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK, Color.GREEN};

    public static final int LEVEL_FINISHING_POINTS = 100;
    public static final int BLOCK_DESTROYING_POINTS = 5;

    public static final int REGIONS = 5;


    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    /**
     * A constructor for the game class.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
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
     * @param f   the used factory.
     * @param br  the BlockRemover listener of the game.
     * @param stl the ScoreTrackingListener listener of the game.
     */
    private void createBlocks(Factory f, BlockRemover br, ScoreTrackingListener stl) {
        int startX = 3, colorIndex = 0;
        for (int y = 3; y < 9; y++) {
            f.createBlockLine(startX, 15, y, COLORS[colorIndex], br, stl);
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
        d.fillRectangle(SIDE_BORDER_SIZE, SCORE_HEIGHT, WIDTH - 2 * SIDE_BORDER_SIZE, HEIGHT - SCORE_HEIGHT);
    }

    /**
     * A method to add 1 to the balls counter of the game.
     */
    public void countBall() {
        this.remainingBalls.increase(1);
    }

    /**
     * A method to initialize the game (creating the game and its game.objects).
     */
    public void initialize() {
        // Creating the GUI
        this.gui = new GUI("Arkanoid", WIDTH, HEIGHT);

        // Creating a factory object
        Factory f = new Factory(this);

        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);

        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);

        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);

        f.createScoreIndicator(this.score);

        // Creating the paddle
        f.createPaddle(PADDLE_START_LOCATION, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        // Creating the balls
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            f.createBall(BALL_START_LOCATION, BALL_RADIUS, BALL_COLOR, BALL_SPEED);
        }

        // Creating the borders
        f.createBorders(BORDER_COLOR);

        f.createDeathBlocks(ballRemover);

        // Creating the blocks
        createBlocks(f, blockRemover, scoreTrackingListener);
    }

    /**
     * A method that check if the level is finished (number of blocks = 0) and closes the GUI if it is.
     *
     * @return true if the level is finished, false otherwise.
     */
    public boolean outOfBlocks() {
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(LEVEL_FINISHING_POINTS);
            this.gui.close();
            return true;
        }
        return false;
    }

    /**
     * A method that checks if the player is dead (number of balls = 0) and closes the GUI if he is.
     *
     * @return true if the player is dead, false otherwise.
     */
    public boolean outOfBalls() {
        if (this.remainingBalls.getValue() == 0) {
            this.gui.close();
            return true;
        }
        return false;
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

            if (this.outOfBlocks() || this.outOfBalls()) {
                return;
            }

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * A method to remove a collidable object from the game.
     *
     * @param c the collidable object that will be deleted.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * A method to remove a sprite object from the game.
     *
     * @param s the sprite item that will be deleted.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
}