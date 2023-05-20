// ID - 212945760

import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

/**
 * AbstractArtDrawing class - A line (actually a line-segment) connects two points -- a start point and an end point.
 * Lines have lengths, and may intersect with other lines. It can also tell if it is the same as another line segment.
 *
 * @author Ori Dabush
 */
public class AbstractArtDrawing {

    /**
     * const values of the HEIGHT and WIDTH of the window.
     */
    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;

    /**
     * a const value of the radius of a point.
     */
    private static final int POINT_RADIUS = 3;

    /**
     * A method that creates a random line in the window.
     *
     * @return the random line that has been created.
     */
    private static Line createRandomLine() {
        Random rand = new Random();
        int x1 = rand.nextInt(WIDTH) + 1; // get integer in range 1 - WIDTH.
        int y1 = rand.nextInt(HEIGHT) + 1; // get integer in range 1 - HEIGHT.
        int x2 = rand.nextInt(WIDTH) + 1; // get integer in range 1 - WIDTH.
        int y2 = rand.nextInt(HEIGHT) + 1; // get integer in range 1 - HEIGHT.
        return new Line(x1, y1, x2, y2);
    }

    /**
     * A method that draws a line on the draw surface.
     *
     * @param l the line.
     * @param d he draw surface.
     */
    private static void drawLine(Line l, DrawSurface d) {
        if (l != null) {
            d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());
        }
    }

    /**
     * A method that draws a point on a draw surface.
     *
     * @param p the point.
     * @param d the draw surface.
     */
    private static void drawPoint(Point p, DrawSurface d) {
        if (p != null) {
            d.fillCircle((int) p.getX(), (int) p.getY(), POINT_RADIUS);
        }
    }

    /**
     * A method that draws 10 random lines and marks all the middle points in blue and all the intersections in red.
     */
    public void drawRandomLines() {
        GUI gui = new GUI("Random Lines Generator", WIDTH, HEIGHT);
        DrawSurface d = gui.getDrawSurface(); // the draw surface
        Line[] linesArr = new Line[10];
        for (int i = 0; i < linesArr.length; ++i) {
            linesArr[i] = createRandomLine();
            d.setColor(Color.BLACK);
            drawLine(linesArr[i], d); // drawing the line
            Point middle = linesArr[i].middle();
            d.setColor(Color.BLUE);
            drawPoint(middle, d); // drawing the middle point
            d.setColor(Color.RED);
            for (int j = 0; j < i; j++) {
                if (linesArr[j].isIntersecting(linesArr[i])) {
                    Point intersection = linesArr[j].intersectionWith(linesArr[i]);
                    drawPoint(intersection, d); // drawing all the intersections
                }
            }
        }
        gui.show(d);
    }

    /**
     * The main method which operates the drawRandomLines method.
     *
     * @param args is ignored right now.
     */
    public static void main(String[] args) {
        AbstractArtDrawing lines = new AbstractArtDrawing();
        /*
         * we call the method drawRandomLines using a instance of the AbstractArtDrawing class because
         * it is a public method.
         */
        lines.drawRandomLines();
    }
}
