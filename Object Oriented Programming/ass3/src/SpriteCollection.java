// ID - 212945760

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class, which creates a list of Sprites and calls their methods using one method only.
 *
 * @author Ori Dabush
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * A constructor for the SpriteCollection class.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * A method to add a given Sprite to the collection.
     *
     * @param s the given Sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * A method to call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : this.sprites) {
            s.timePassed();
        }
    }

    /**
     * A method to call drawOn(d) on all sprites, using a given DrawSurface.
     *
     * @param d the given DrawSurface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}