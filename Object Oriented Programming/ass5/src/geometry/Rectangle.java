// ID - 212945760

package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * The geometry.Rectangle class, which creates a rectangle from top left point and height and width parameters.
 *
 * @author Ori Dabush
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * A constructor that creates a new rectangle with location and width/height.
     *
     * @param upperLeft the top left point of the rectangle.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
    }

    /**
     * A constructor that creates a new rectangle with x and y values and width/height.
     *
     * @param x      the x value of the upper left point.
     * @param y      the y value of the upper left point.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(double x, double y, double width, double height) {
        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * A constructor to copy a rectangle values from another rectangle.
     * It will be used when we will want to create different rectangles from one rectangle.
     *
     * @param r the rectangle we copy its values.
     */
    public Rectangle(Rectangle r) {
        this.upperLeft = new Point(r.upperLeft);
        this.width = r.width;
        this.height = r.height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the given line.
     * @return the list if there are any intersections, null otherwise.
     */
    public List<Point> intersectionPoints(Line line) {
        if (line == null) {
            return null;
        }
        List<Point> list = new ArrayList<Point>();
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point bottomLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point bottomRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        Line upperEdge = new Line(this.upperLeft, upperRight);
        Line bottomEdge = new Line(bottomLeft, bottomRight);
        Line rightEdge = new Line(upperRight, bottomRight);
        Line leftEdge = new Line(this.upperLeft, bottomLeft);
        // case of line that is on one of the edges of the rectangle.
        if (line.isOn(upperEdge) || line.isOn(bottomEdge) || line.isOn(rightEdge) || line.isOn(leftEdge)) {
            return list;
        }
        Point intersection;
        intersection = line.intersectionWith(upperEdge);
        //checking the intersection between the line and every edge of the rectangle.
        if (line.isIntersecting(upperEdge) && !intersection.isPointInList(list)) {
            list.add(intersection);
        }
        intersection = line.intersectionWith(bottomEdge);
        if (line.isIntersecting(bottomEdge) && !intersection.isPointInList(list)) {
            list.add(intersection);
        }
        intersection = line.intersectionWith(rightEdge);
        if (line.isIntersecting(rightEdge) && !intersection.isPointInList(list)) {
            list.add(intersection);
        }
        intersection = line.intersectionWith(leftEdge);
        if (line.isIntersecting(leftEdge) && !intersection.isPointInList(list)) {
            list.add(intersection);
        }
        return list;
    }

    /**
     * An access method to the width of the rectangle.
     *
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * An access method to the height of the rectangle.
     *
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * An access method to the upper left point of the rectangle.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * A method to move the rectangle horizontal numOfPixels pixels right.
     *
     * @param numOfPixels the number of pixels we want to move.
     */
    public void moveRectangleHorizontal(int numOfPixels) {
        this.upperLeft = new Point(this.upperLeft.getX() + numOfPixels, this.upperLeft.getY());
    }
}