// ID - 212945760

package game.operation;

import extras.Counter;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.events.ScoreIndicator;
import game.listeners.ScoreTrackingListener;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Paddle;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.Random;

/**
 * The gameOperation.Factory class, which will be used to create the game's elements and add them to the game.
 *
 * @author Ori Dabush
 */
public class Factory {

    private Game game;

    /**
     * A constructor for the gameOperation.Factory class.
     *
     * @param g the game we want to create elements for.
     */
    public Factory(Game g) {
        this.game = g;
    }

    /**
     * A method to create a paddle and add it to the game.
     *
     * @param upperLeft the paddle's upper-left point.
     * @param width     the paddle's width.
     * @param height    the paddle's height.
     * @param c         the paddle's color.
     */
    public void createPaddle(Point upperLeft, int width, int height, Color c) {
        Rectangle r = new Rectangle(upperLeft, width, height);
        Paddle p = new Paddle(r, c, this.game.getKeyboardSensor());
        p.addToGame(this.game);
    }

    /**
     * A method to create a ball and add it to the game.
     *
     * @param startLocation the ball's start location.
     * @param r             the ball's radius.
     * @param c             the ball's color.
     * @param speed         the ball's speed (not velocity).
     */
    public void createBall(Point startLocation, int r, Color c, double speed) {
        Random rand = new Random();
        // Random angle between -45 to 45
        int angle = rand.nextInt(91) - 45;
        Ball ball = new Ball(startLocation, r, c);
        ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
        ball.setEnv(this.game.getEnvironment());
        ball.addToGame(this.game);
        this.game.countBall();
    }

    /**
     * A method to create the borders of the screen.
     *
     * @param c the borders' color.
     */
    public void createBorders(Color c) {
        Block[] borders = new Block[3];
        borders[0] = new Block(new Rectangle(0, Game.SCORE_HEIGHT, Game.WIDTH, Game.TOP_BORDER_SIZE), c);
        borders[1] = new Block(new Rectangle(0, Game.SCORE_HEIGHT + Game.TOP_BORDER_SIZE,
                Game.SIDE_BORDER_SIZE, Game.HEIGHT - Game.SCORE_HEIGHT - Game.TOP_BORDER_SIZE), c);
        borders[2] = new Block(new Rectangle(
                Game.WIDTH - Game.SIDE_BORDER_SIZE, Game.SCORE_HEIGHT + Game.TOP_BORDER_SIZE,
                Game.SIDE_BORDER_SIZE, Game.HEIGHT - Game.SCORE_HEIGHT - Game.TOP_BORDER_SIZE), c);
//        borders[3] = new Block(new Rectangle(0, HEIGHT - BORDER_SIZE, WIDTH, BORDER_SIZE), c);
        for (Block border : borders) {
            border.addToGame(this.game);
        }
    }

    /**
     * A method to create the death block in the bottom of the screen.
     *
     * @param br the BallRemover of the game.
     */
    public void createDeathBlocks(BallRemover br) {
        Block deathBlock = new Block(new Rectangle(0, Game.HEIGHT, Game.WIDTH, 0), Color.WHITE);
        deathBlock.addHitListener(br);
        deathBlock.addToGame(this.game);
    }

    /**
     * Assuming that the screen is a block's array, with 15 blocks (indexes 0-14) in a line and 20 block in a column
     * (indexes 0-19), this is a method to create a block in the location (x,y) of this array.
     *
     * @param x   the block's location x value.
     * @param y   the block's location y value.
     * @param c   the block's color.
     * @param br  the BallRemover of the game.
     * @param stl the ScoreTrackingListener of the game.
     */
    public void createBlock(int x, int y, Color c, BlockRemover br, ScoreTrackingListener stl) {
        int upperLeftX = Game.SIDE_BORDER_SIZE + x * Game.BLOCK_WIDTH;
        int upperLeftY = Game.SCORE_HEIGHT + Game.TOP_BORDER_SIZE + y * Game.BLOCK_HEIGHT;
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        Block b = new Block(new Rectangle(upperLeft, Game.BLOCK_WIDTH, Game.BLOCK_HEIGHT), c);
        b.addHitListener(br);
        b.addHitListener(stl);
        br.getRemainingBlocks().increase(1);
        b.addToGame(this.game);
    }

    /**
     * Assuming that the screen is a block's array, with 15 blocks (indexes 0-14) in a line and 20 block in a column
     * (indexes 0-19), this is a method to create a line of blocks in the lineIndex line of the array, starting from
     * startX and ending in endX.
     *
     * @param startX    the first block's x value.
     * @param endX      the last block's x value.
     * @param lineIndex the index of the line.
     * @param c         the blocks' color.
     * @param br        the BallRemover of the game.
     * @param stl       the ScoreTrackingListener of the game.
     */
    public void createBlockLine(int startX, int endX, int lineIndex, Color c,
                                BlockRemover br, ScoreTrackingListener stl) {
        for (int i = startX; i < endX; i++) {
            createBlock(i, lineIndex, c, br, stl);
        }
    }

    /**
     * Assuming that the screen is a block's array, with 15 blocks (indexes 0-14) in a line and 20 block in a column
     * (indexes 0-19), this is a method to create a column of blocks in the columnIndex column of the array,
     * starting from startY and ending in endY.
     *
     * @param startY      the first block's y value.
     * @param endY        the last block's y value.
     * @param columnIndex the index of the column.
     * @param c           the blocks' color.
     * @param br          the BallRemover of the game.
     * @param stl         the ScoreTrackingListener of the game.
     */
    public void createBlockColumn(int startY, int endY, int columnIndex, Color c,
                                  BlockRemover br, ScoreTrackingListener stl) {
        for (int i = startY; i <= endY; i++) {
            createBlock(columnIndex, i, c, br, stl);
        }
    }

    /**
     * A method to create a ScoreIndicator for the game.
     *
     * @param scoreCounter the score's counter of the game.
     */
    public void createScoreIndicator(Counter scoreCounter) {
        ScoreIndicator si = new ScoreIndicator(new Rectangle(0, 0, Game.SCORE_WIDTH, Game.SCORE_HEIGHT), scoreCounter);
        si.addToGame(this.game);
    }
}
