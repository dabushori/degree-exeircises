// ID - 212945760

package menu;

import game.animation.Animation;

/**
 * This is the Menu interface, which is a menu with a few options to get.
 *
 * @param <T> the type of the options the menu is returning.
 * @author Ori Dabush.
 */
public interface Menu<T> extends Animation {

    /**
     * A method to add an option to the menu.
     *
     * @param key       the key that needs to be pressed to get the option.
     * @param message   the message that is seen on the screen of the menu.
     * @param returnVal the value of the option that is added.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * A method to get the status of the menu, which is the option that was chosen and null if no option was chosen.
     *
     * @return the option that was chosen and null if no option was chosen.
     */
    T getStatus();
}