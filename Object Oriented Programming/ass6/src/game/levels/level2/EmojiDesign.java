// ID - 212945760

package game.levels.level2;

import biuoop.DrawSurface;
import game.levels.Background;
import geometry.Point;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This is the EmojiDesign class, which is the background of level 2.
 *
 * @author Ori Dabush.
 */
public class EmojiDesign extends Background {
    private static Random rand = new Random();

    private List<Point> emojies;

    /**
     * A constructor for the EmojiDesign class.
     */
    public EmojiDesign() {
        this.emojies = new LinkedList<>();
        createScreen();
    }

    /**
     * A method to create a line of emojies.
     *
     * @param x the first emoji's x value.
     * @param y the y value of the line.
     */
    public void createEmojiesLine(int x, int y) {
        for (int i = x; i < 830; i += 60) {
            this.emojies.add(new Point(i, y));
        }
    }

    /**
     * A method to create the screens of the level.
     */
    public void createScreen() {
        int x1 = 30, x2 = 0;
        boolean flag = true;
        for (int y = 30; y < 580; y += 60) {
            if (flag) {
                createEmojiesLine(x1, y);
            } else {
                createEmojiesLine(x2, y);
            }
            flag = !flag;
        }
    }

    /**
     * A method to draw an emoji on a given DrawSurface.
     *
     * @param d the given DrawSurface.
     * @param p the center of the emoji.
     */
    public void drawEmoji(DrawSurface d, Point p) {
        d.setColor(Color.YELLOW);
        d.fillCircle((int) p.getX(), (int) p.getY(), 20);
        d.setColor(Color.BLACK);
        d.drawCircle((int) p.getX(), (int) p.getY(), 20);

        d.drawCircle((int) p.getX(), (int) p.getY(), 10);

        d.setColor(Color.YELLOW);
        d.fillRectangle((int) p.getX() - 14, (int) p.getY() - 10, 28, 15);

        d.setColor(Color.BLACK);

        d.fillCircle((int) p.getX() - 7, (int) p.getY() - 5, 2);
        d.fillCircle((int) p.getX() + 7, (int) p.getY() - 5, 2);
    }

    /**
     * A method to draw all the emojies in the current object's list.
     *
     * @param d the given DrawSurface.
     */
    public void drawEmojies(DrawSurface d) {
        for (Point p : this.emojies) {
            drawEmoji(d, p);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);
        drawEmojies(d);
    }
}
