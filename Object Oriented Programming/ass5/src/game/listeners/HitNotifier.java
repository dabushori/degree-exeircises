// ID - 212945760

package game.listeners;

/**
 * This is the HitNotifier interface, which will be used to notify about hit events.
 *
 * @author Ori Dabush.
 */
public interface HitNotifier {
    /**
     * A method to add a HitListener to send hit notification to.
     *
     * @param hl the HitListener that will be added.
     */
    void addHitListener(HitListener hl);

    /**
     * a method to remove a HitListener from the list of listeners.
     *
     * @param hl the HitListener that will be removed.
     */
    void removeHitListener(HitListener hl);
}