// ID - 212945760

package game.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import game.levels.GameLevel;

/**
 * this is the AnimationRunner class, which will be used to run animations.
 *
 * @author Ori Dabush.
 */
public class AnimationRunner {
    private int framesPerSecond;
    private Sleeper sleeper;
    private GUI gui;

    /**
     * A constructor for the AnimationRunner class.
     */
    public AnimationRunner() {
        this.gui = new GUI("Arkanoid", GameLevel.WIDTH, GameLevel.HEIGHT);
        this.framesPerSecond = 60;
        this.sleeper = new Sleeper();
    }

    /**
     * A method to run a given animation.
     *
     * @param animation the given animation.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d);
            this.gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * A method to get a KeyboardSensor from the current GUI.
     *
     * @return a KeyboardSensor from the current GUI.
     */
    public KeyboardSensor getKeyboardSensor() {
        return this.gui.getKeyboardSensor();
    }

    /**
     * A method to close the current GUI.
     */
    public void closeGUI() {
        this.gui.close();
    }
}