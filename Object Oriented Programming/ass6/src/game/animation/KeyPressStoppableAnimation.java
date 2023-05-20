// ID - 212945760

package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * This is the KeyPressStoppableAnimation class, which is an animation that needs to be stop when a
 * specific key is pressed.
 *
 * @author Ori Dabush.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboardSensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * A constructor for the KeyPressStoppableAnimation class.
     *
     * @param sensor    a KeyboardSensor of the game.
     * @param key       the key that needs to be pressed.
     * @param animation the animation that will be stopped when the key is pressed.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboardSensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        if (keyboardSensor.isPressed(key)) {
            if (!isAlreadyPressed) {
                stop = true;
            }
        }
        isAlreadyPressed = false;
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}