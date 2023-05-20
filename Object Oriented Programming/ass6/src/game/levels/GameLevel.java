// ID - 212945760

package game.levels;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import extras.Counter;
import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.CountdownAnimation;
import game.animation.KeyPressStoppableAnimation;
import game.animation.LoseScreen;
import game.animation.PauseScreen;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.ScoreTrackingListener;
import game.objects.Block;
import game.objects.Collidable;
import game.objects.Sprite;
import game.events.GameEnvironment;
import game.events.SpriteCollection;
import game.operation.Factory;
import geometry.Point;

/**
 * The gameOperation.Game class, which will make the game creating easier.
 *
 * @author Ori Dabush
 */
public class GameLevel implements Animation {

    // Sizes of the screen.
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int SCORE_WIDTH = WIDTH;
    public static final int SCORE_HEIGHT = 20;

    // Sizes and start location of the paddle.
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 5;
    public static final Point PADDLE_START_LOCATION = new Point(350, HEIGHT - PADDLE_HEIGHT);

    // parameters of the ball.
    public static final Point BALL_START_LOCATION = new
            Point(PADDLE_START_LOCATION.getX() + PADDLE_WIDTH / 2, PADDLE_START_LOCATION.getY() - 2);
    public static final int BALL_RADIUS = 5;

    public static final int LEVEL_FINISHING_POINTS = 100;
    public static final int BLOCK_DESTROYING_POINTS = 5;

    public static final int REGIONS = 5;


    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private AnimationRunner runner;
    private boolean running;
    private Factory factory;
    private LevelInformation level;
    private KeyboardSensor keyboardSensor;

    /**
     * A constructor for the game class.
     *
     * @param level  the level we run.
     * @param runner the AnimationRunner that will run the level.
     * @param score  the score of the player.
     */
    public GameLevel(LevelInformation level, AnimationRunner runner, Counter score) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = score;
        this.running = true;
        this.factory = new Factory(this);
        this.runner = runner;
        this.level = level;
        this.keyboardSensor = runner.getKeyboardSensor();
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

    /**
     * A method to get a keyboard sensor from the current object's gui.
     *
     * @return a keyboard sensor.
     */
    public KeyboardSensor getKeyboardSensor() {
        return this.keyboardSensor;
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
     * @param br  the BlockRemover listener of the game.
     * @param stl the ScoreTrackingListener listener of the game.
     */
    private void createBlocks(BlockRemover br, ScoreTrackingListener stl) {
        for (Block b : level.blocks()) {
            b.addHitListener(br);
            b.addHitListener(stl);
            this.remainingBlocks.increase(1);
            b.addToGame(this);
        }
    }

    /**
     * A method to add the background to the game.
     */
    public void createBackground() {
        level.getBackground().addToGame(this);
    }

    /**
     * A method to add 1 to the balls counter of the game.
     */
    public void countBall() {
        this.remainingBalls.increase(1);
    }

    /**
     * A method to write the level's name.
     */
    public void createLevelName() {
        Sprite levelName = new Massage("Level Name: " + level.levelName(), new Point(10, 19),
                20, level.textColor());
        levelName.addToGame(this);
    }

    /**
     * A method to initialize the game (creating the game and its game.objects).
     */
    public void initialize() {
        createBackground();

        // Creating the score indicator.
        this.factory.createScoreIndicator(this.score, level.textColor());

        // Creating the paddle.
        Point upperLeft = new Point(WIDTH / 2 - level.paddleWidth() / 2, HEIGHT - PADDLE_HEIGHT);
        this.factory.createPaddle(upperLeft, level.paddleWidth(), PADDLE_HEIGHT, level.paddleColor(),
                level.paddleSpeed());

        // Creating the borders.
        this.factory.createBorders();

        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);

        // Creating the death blocks.
        this.factory.createDeathBlocks(ballRemover);

        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);

        // Creating the blocks.
        createBlocks(blockRemover, scoreTrackingListener);

        // Creating the balls.
        this.factory.createBalls(BALL_START_LOCATION, BALL_RADIUS, level.ballsColor(), level.ballsBorderColor(),
                level.initialBallVelocities(), level.numberOfBalls());

        createLevelName();
    }

    /**
     * A method that check if the level is finished (number of blocks = 0) and closes the GUI if it is.
     *
     * @return true if the level is finished, false otherwise.
     */
    public boolean outOfBlocks() {
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(LEVEL_FINISHING_POINTS);
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
            this.runner.run(new KeyPressStoppableAnimation(getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                    new LoseScreen(sprites, level.textColor(), score.getValue())));
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        this.running = !(this.outOfBalls() || this.outOfBlocks());
        this.checkIfPaused();
    }

    /**
     * A method to check if the game is paused, and create a pause screen if it is paused.
     */
    public void checkIfPaused() {
        if (this.getKeyboardSensor().isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.sprites, level.textColor())));
            this.runner.run(new CountdownAnimation(2, 3, this.sprites, level.textColor()));
        }
    }

    /**
     * A method to run the game.animation loop.
     *
     * @return the sprites of the game, so that we can use them in the future.
     */
    public SpriteCollection run() {
        this.runner.run(new CountdownAnimation(2, 3, this.sprites, level.textColor()));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
        return this.sprites;
    }
}