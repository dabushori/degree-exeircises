// ID - 212945760

package game.levels.level4;

import biuoop.DrawSurface;
import game.levels.Background;

import java.awt.Color;
import java.util.Random;

/**
 * This is the MatrixDesign class, which is the background of level 4.
 *
 * @author Ori Dabush.
 */
public class MatrixDesign extends Background {
    public static final Color MATRIX_COLOR = new Color(42, 228, 59);
    public static final Color MATRIX_FADED_COLOR = new Color(6, 82, 13);

    private static Random rand = new Random();

    /**
     * A method to draw a column of 0 and 1s.
     *
     * @param d      the DrawSurface.
     * @param x      the x of the column.
     * @param length the length of the column.
     * @param c      the color of the column.
     */
    public static void drawColumn(DrawSurface d, int x, int length, Color c) {
        int y = 10;
        d.setColor(c);
        for (int i = 0; i < length; i++) {
            d.drawText(x, y, "" + rand.nextInt(2), 15);
            y += 15;
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        for (int i = 0; i < 800; i += 10) {
            for (int j = 0; j < 2; j++) {
                drawColumn(d, i + rand.nextInt(20) - 10, rand.nextInt(30), MATRIX_COLOR);
                drawColumn(d, i + rand.nextInt(20) - 10, rand.nextInt(30), MATRIX_FADED_COLOR);
            }
        }
    }
}