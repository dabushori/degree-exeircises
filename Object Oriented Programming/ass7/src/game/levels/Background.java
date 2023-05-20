// ID - 212945760

package game.levels;

import biuoop.DrawSurface;
import game.objects.Sprite;

/**
 * This is the Background class, which represents a background of a level.
 *
 * @author Ori Dabush.
 */
public abstract class Background implements Sprite {
    @Override
    public abstract void drawOn(DrawSurface d);

    @Override
    public void timePassed() {
        // do nothing
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
