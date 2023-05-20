// ID - 212945760

package menu.tasks;

import biuoop.KeyboardSensor;
import game.animation.AnimationRunner;
import game.animation.KeyPressStoppableAnimation;
import menu.HighestScore;

/**
 * This is the ShowHiScoresTask class which responsible for showing the highest score.
 *
 * @author Ori Dabush
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;

    /**
     * A constructor for the ShowHiScoresTask class.
     *
     * @param runner the animation-runner that will run the task.
     */
    public ShowHiScoresTask(AnimationRunner runner) {
        this.runner = runner;
    }

    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(
                runner.getKeyboardSensor(), KeyboardSensor.SPACE_KEY, new HighestScore()));
        return null;
    }
}