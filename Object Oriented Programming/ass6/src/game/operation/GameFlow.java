package game.operation;

import biuoop.KeyboardSensor;
import extras.Counter;
import game.animation.AnimationRunner;
import game.animation.KeyPressStoppableAnimation;
import game.animation.WinScreen;
import game.events.SpriteCollection;
import game.levels.GameLevel;
import game.levels.LevelInformation;

import java.awt.Color;
import java.util.List;

/**
 * This is the GameFlow class, which is responsible of running the levels one by one.
 *
 * @author Ori Dabush.
 */
public class GameFlow {

    private AnimationRunner runner;
    private KeyboardSensor keyboardSensor;
    private Counter score;

    /**
     * A constructor for the GameFlow class.
     *
     * @param ar the animation runner that will run the levels.
     */
    public GameFlow(AnimationRunner ar) {
        this.runner = ar;
        this.keyboardSensor = ar.getKeyboardSensor();
        this.score = new Counter();
    }

    /**
     * A method to run a given list of levels.
     *
     * @param levels the given list of levels.
     */
    public void runLevels(List<LevelInformation> levels) {
        SpriteCollection sc = new SpriteCollection();
        Color textColor = Color.BLACK;
        boolean win = true;
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.runner, this.score);

            level.initialize();

            sc = level.run();
            textColor = levelInfo.textColor();

            if (level.outOfBalls()) {
                win = false;
                break;
            }
        }
        if (win) {
            this.runner.run(new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new WinScreen(sc, textColor, score.getValue())));
        }
        this.runner.closeGUI();
    }
}
