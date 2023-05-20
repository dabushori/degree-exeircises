// ID - 212945760

package game.events;

import biuoop.DrawSurface;
import game.objects.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The gameEvents.SpriteCollection class, which creates a list of Sprites and calls their methods using one method only.
 *
 * @author Ori Dabush
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * A constructor for the gameEvents.SpriteCollection class.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * A method to add a given gameEvents.Sprite to the collection.
     *
     * @param s the given gameEvents.Sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * A method to call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * A method to call drawOn(d) on all sprites, using a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.drawOn(d);
        }
    }

    /**
     * A method to remove a sprite object from the collection.
     *
     * @param s the object which will be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}