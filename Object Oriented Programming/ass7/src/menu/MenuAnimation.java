// ID - 212945760

package menu;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.levels.level2.EmojiDesign;
import game.objects.Sprite;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the MenuAnimation class, which is an implementation of the Menu interface.
 *
 * @param <T> the type of the options the menu is returning.
 * @author Ori Dabush.
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<String> keys;
    private List<String> messages;
    private List<T> returnVals;
    private String title;
    private KeyboardSensor sensor;

    /**
     * A constructor for the MenuAnimation class.
     *
     * @param title the title of the menu.
     * @param ks    the sensor that the menu will use.
     */
    public MenuAnimation(String title, KeyboardSensor ks) {
        this.keys = new LinkedList<>();
        this.messages = new LinkedList<>();
        this.returnVals = new LinkedList<>();
        this.title = title;
        this.sensor = ks;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnVals.add(returnVal);
    }

    @Override
    public T getStatus() {

        for (String key : this.keys) {
            int index = this.keys.indexOf(key);
            if (this.sensor.isPressed(key)) {
                return this.returnVals.get(index);
            }
        }
        return null;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        Sprite background = new EmojiDesign();
        background.drawOn(d);
        int x = 50, y = 100;
        d.drawText(x, y, this.title, 50);
        y += 50;
        for (String message : this.messages) {
            d.drawText(x, y, message, 35);
            y += 40;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.getStatus() != null;
    }
}
