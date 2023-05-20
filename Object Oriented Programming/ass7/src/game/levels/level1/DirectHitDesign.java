// ID - 212945760

package game.levels.level1;

import biuoop.DrawSurface;
import game.levels.Background;

import java.awt.Color;

/**
 * This is the DirectHitDesign, which is the background design of level 1.
 *
 * @author Ori Dabush.
 */
public class DirectHitDesign extends Background {
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
    }
}
