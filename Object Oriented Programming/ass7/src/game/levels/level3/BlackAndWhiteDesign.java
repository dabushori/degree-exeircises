// ID - 212945760

package game.levels.level3;

import biuoop.DrawSurface;
import game.levels.Background;
import game.levels.GameLevel;

import java.awt.Polygon;
import java.awt.Color;

/**
 * This is the BlackAndWhiteDesign, which is the background of level 3.
 *
 * @author Ori Dabush.
 */
public class BlackAndWhiteDesign extends Background {
    private static int heightOfLayer = 60;
    private static int widthOfDiff = (800 * heightOfLayer) / 600;

    /**
     * A method to draw the up and down shapes of thee design.
     *
     * @param d               the DrawSurface.
     * @param firstX          the x the shape starts from.
     * @param lastX           the x the shape ends at.
     * @param y               the y value of the current layer.
     * @param startsWithWhite true if the layer starts with white, false otherwise.
     */
    public void drawByX(DrawSurface d, int firstX, int lastX, int y, boolean startsWithWhite) {
        int diff = (lastX - firstX) / 4;
        int midX = 400, midY = 300;
        boolean isWhite = startsWithWhite;
        if (y == midY) {
            return;
        } else if (y < midY) {
            for (int i = 0; i < 4; i++) {
                Polygon p = new Polygon();
                p.addPoint(midX, midY);
                p.addPoint(firstX + i * diff, y);
                p.addPoint(firstX + (i + 1) * diff, y);
                if (isWhite) {
                    d.setColor(Color.WHITE);
                } else {
                    d.setColor(Color.BLACK);
                }
                isWhite = !isWhite;
                d.fillPolygon(p);
            }
            drawByX(d, firstX + widthOfDiff, lastX - widthOfDiff, y + heightOfLayer, !startsWithWhite);
        } else {
            for (int i = 0; i < 4; i++) {
                Polygon p = new Polygon();
                p.addPoint(midX, midY);
                p.addPoint(firstX + i * diff, y);
                p.addPoint(firstX + (i + 1) * diff, y);
                if (isWhite) {
                    d.setColor(Color.WHITE);
                } else {
                    d.setColor(Color.BLACK);
                }
                isWhite = !isWhite;
                d.fillPolygon(p);
            }
            drawByX(d, firstX + widthOfDiff, lastX - widthOfDiff, y - heightOfLayer, !startsWithWhite);
        }
    }

    /**
     * A method to draw the left and right shapes of thee design.
     *
     * @param d               the DrawSurface.
     * @param firstY          the y the shape starts from.
     * @param lastY           the y the shape ends at.
     * @param x               the x value of the current layer.
     * @param startsWithWhite true if the layer starts with white, false otherwise.
     */
    public void drawByY(DrawSurface d, int firstY, int lastY, int x, boolean startsWithWhite) {
        int diff = (lastY - firstY) / 4;
        int midX = 400, midY = 300;
        boolean isWhite = startsWithWhite;
        if (x == midX) {
            return;
        } else if (x < midX) {
            for (int i = 0; i < 4; i++) {
                Polygon p = new Polygon();
                p.addPoint(midX, midY);
                p.addPoint(x, firstY + i * diff);
                p.addPoint(x, firstY + (i + 1) * diff);
                if (isWhite) {
                    d.setColor(Color.WHITE);
                } else {
                    d.setColor(Color.BLACK);
                }
                isWhite = !isWhite;
                d.fillPolygon(p);
            }
            drawByY(d, firstY + heightOfLayer, lastY - heightOfLayer, x + widthOfDiff, !startsWithWhite);
        } else {
            for (int i = 0; i < 4; i++) {
                Polygon p = new Polygon();
                p.addPoint(midX, midY);
                p.addPoint(x, firstY + i * diff);
                p.addPoint(x, firstY + (i + 1) * diff);
                if (isWhite) {
                    d.setColor(Color.WHITE);
                } else {
                    d.setColor(Color.BLACK);
                }
                isWhite = !isWhite;
                d.fillPolygon(p);
            }
            drawByY(d, firstY + heightOfLayer, lastY - heightOfLayer, x - widthOfDiff, !startsWithWhite);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        BlackAndWhiteDesign bw = new BlackAndWhiteDesign();
        bw.drawByX(d, 0, GameLevel.WIDTH, 0, true);
        bw.drawByX(d, 0, GameLevel.WIDTH, GameLevel.HEIGHT, false);
        bw.drawByY(d, 0, GameLevel.HEIGHT, 0, false);
        bw.drawByY(d, 0, GameLevel.HEIGHT, GameLevel.WIDTH, true);
    }
}