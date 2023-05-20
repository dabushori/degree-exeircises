// ID - 212945760

import java.util.List;

/**
 * Point class - A point has an x and a y value, and can measure the distance to other points, and
 * if it is equal to another point.
 *
 * @author Ori Dabush
 */
public class Point {

    /**
     * Epsilon is a const to compare between double numbers.
     */
    private static final double EPSILON = Math.pow(10, -8);

    /**
     * A method to compare between 2 double variables using epsilon const.
     *
     * @param d1 the first double variable.
     * @param d2 the second double variable.
     * @return true if they are equal, false otherwise.
     */
    private static boolean cmpDouble(double d1, double d2) {
        return Math.abs(d1 - d2) <= EPSILON;
    }

    /**
     * The x and y values.
     */
    private double x;
    private double y;

    /**
     * The constructor.
     *
     * @param x the point's x value.
     * @param y the point's y value.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A constructor to copy a point values from another point.
     * It will be used when we will want to create different points from one point.
     *
     * @param p the point we copy its values.
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * A method that calculates the distance between the current point and another point.
     *
     * @param other the point we want to calculate the distance from.
     * @return the distance of this point to the other point, -1 if one of the points is null.
     */
    public double distance(Point other) {
        if (other == null) {
            throw new RuntimeException("distance with null point!");
        }
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * A method that checks if the current point is equal to another point.
     *
     * @param other the point we want to compare the current point to.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return cmpDouble(this.x, other.x) && cmpDouble(this.y, other.y);
    }

    /**
     * @return the x value of the current point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y value of the current point
     */
    public double getY() {
        return this.y;
    }

    /**
     * A method to check if the point is in a given list of points.
     *
     * @param list the list of the points.
     * @return true if it is in the list, false otherwise.
     */
    public boolean isPointInList(List<Point> list) {
        if (list == null) {
            return false;
        }
        for (Point p : list) {
            if (p.equals(this)) {
                return true;
            }
        }
        return false;
    }
}