// ID - 212945760

package game.operation;

import extras.Counter;
import game.levels.GameLevel;
import game.listeners.BallRemover;
import game.events.ScoreIndicator;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Paddle;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.List;


/**
 * The gameOperation.Factory class, which will be used to create the game's elements and add them to the game.
 *
 * @author Ori Dabush
 */
public class Factory {
    private GameLevel gameLevel;

    /**
     * A constructor for the gameOperation.Factory class.
     *
     * @param g the game we want to create elements for.
     */
    public Factory(GameLevel g) {
        this.gameLevel = g;
    }

    /**
     * A method to create a paddle and add it to the game.
     *
     * @param upperLeft the paddle's upper-left point.
     * @param width     the paddle's width.
     * @param height    the paddle's height.
     * @param c         the paddle's color.
     * @param speed     the speed of the paddle.
     */
    public void createPaddle(Point upperLeft, int width, int height, Color c, int speed) {
        Rectangle r = new Rectangle(upperLeft, width, height);
        Paddle p = new Paddle(r, c, this.gameLevel.getKeyboardSensor(), speed);
        p.addToGame(this.gameLevel);
    }

    /**
     * A method to create the balls.
     *
     * @param start       the balls' start location.
     * @param r           the balls' radius.
     * @param c           the balls' color.
     * @param velocities  the balls' velocities.
     * @param numOfBalls  the number of balls.
     * @param borderColor the color of the border of the balls.
     */
    public void createBalls(Point start, int r, Color c, Color borderColor, List<Velocity> velocities, int numOfBalls) {
        for (int i = 0; i < numOfBalls; i++) {
            Ball ball = new Ball(start, r, c, borderColor);
            ball.setVelocity(velocities.get(i));
            ball.setEnv(this.gameLevel.getEnvironment());
            ball.addToGame(this.gameLevel);
            this.gameLevel.countBall();
        }
    }

    /**
     * A method to create the borders of the screen.
     */
    public void createBorders() {
        Block[] borders = new Block[3];
        Color c = Color.LIGHT_GRAY;
        borders[0] = new Block(new Rectangle(0, GameLevel.SCORE_HEIGHT, GameLevel.WIDTH, 0), c);
        borders[1] = new Block(new Rectangle(0, GameLevel.SCORE_HEIGHT, 0,
                GameLevel.HEIGHT - GameLevel.SCORE_HEIGHT), c);
        borders[2] = new Block(new Rectangle(GameLevel.WIDTH, GameLevel.SCORE_HEIGHT,
                0, GameLevel.HEIGHT - GameLevel.SCORE_HEIGHT), c);
        for (Block border : borders) {
            border.addToGame(this.gameLevel);
        }
    }

    /**
     * A method to create the death block in the bottom of the screen.
     *
     * @param br the BallRemover of the game.
     */
    public void createDeathBlocks(BallRemover br) {
        Block deathBlock = new Block(new Rectangle(0, GameLevel.HEIGHT, GameLevel.WIDTH, 0), Color.WHITE);
        deathBlock.addHitListener(br);
        deathBlock.addToGame(this.gameLevel);
    }

    /**
     * A method to create a ScoreIndicator for the game.
     *
     * @param scoreCounter the score's counter of the game.
     * @param c            the score rectangle's color.
     */
    public void createScoreIndicator(Counter scoreCounter, Color c) {
        ScoreIndicator si = new ScoreIndicator(
                new Rectangle(0, 0, GameLevel.SCORE_WIDTH, GameLevel.SCORE_HEIGHT), scoreCounter, c);
        si.addToGame(this.gameLevel);
    }
}