// ID - 212945760

package geometry;

import extras.Compare;

import java.util.List;

/**
 * geometry.Line class - A line (actually a line-segment) connects two points -- a start point and an end point.
 * Lines have lengths, and may intersect with other lines. It can also tell if it is the same as another line segment.
 *
 * @author Ori Dabush
 */
public class Line {

    /**
     * The start and end points of the line.
     */
    private Point start;
    private Point end;

    /**
     * A constructor that creates a line from 2 points.
     *
     * @param start the start point of the line.
     * @param end   the end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start);
        this.end = new Point(end);
    }

    /**
     * A constructor that creates a line from 2 x and 2 y values.
     *
     * @param x1 the start point x value.
     * @param y1 the start point y value.
     * @param x2 the end point x value.
     * @param y2 the end point y value.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * A method that calculates the length of the line.
     *
     * @return the length of the line, -1 if one of the points (start / end) is null.
     */
    public double length() {
        if (this.start == null || this.end == null) {
            return -1;
        }
        return this.start.distance(this.end);
    }

    /**
     * A method that calculates the middle point of the line.
     *
     * @return the middle point of the line, and null if one of the points (start / end) is null.
     */
    public Point middle() {
        if (this.start == null || this.end == null) {
            return null;
        }
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * A method that finds the current line's slope, if it doesn't have a slope (x=5 for example), it returns null.
     *
     * @return the slope of the current line.
     */
    private Double slope() {
        if (this.start == null || this.end == null) {
            return null;
        }
        if (Compare.cmpDouble(this.start.getX(), this.end.getX())) {
            return null;
        }
        return (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
    }

    /**
     * A method that checks if the point is in the range of the line.
     *
     * @param p the point we would like to check
     * @return true if it is in the range, false otherwise.
     */
    private boolean isPointInRange(Point p) {
        if (p == null || this.start == null || this.end == null) {
            return false;
        }
        return (((this.start.getX() <= p.getX() && p.getX() <= this.end.getX())
                || (this.end.getX() <= p.getX() && p.getX() <= this.start.getX()))
                && ((this.start.getY() <= p.getY() && p.getY() <= this.end.getY())
                || (this.end.getY() <= p.getY() && p.getY() <= this.start.getY())));
    }

    /**
     * A method to check if a given point is on the current line.
     *
     * @param p the given point.
     * @return true if it is on the line, false otherwise.
     */
    public boolean isPointOnLine(Point p) {
        if (p == null) {
            return false;
        }
        if (this.slope() == null) {
            return ((this.start.getY() < p.getY() && p.getY() < this.end.getY())
                    || (this.end.getY() < p.getY() && p.getY() < this.start.getY())
                    || (p.equals(start)) || p.equals(end))
                    && Compare.cmpDouble(this.start.getX(), p.getX());
        }
        if (Compare.cmpDouble(this.slope(), 0.0)) {
            return this.isPointInRange(p) && Compare.cmpDouble(this.start.getY(), p.getY());
        }
        if (this.isPointInRange(p)
                && Compare.cmpDouble(p.getY(), this.slope() * (p.getX() - this.start.getX()) + this.start.getY())) {
            return true;
        }
        return false;
    }

    /**
     * A method that checks if two lines have the same line equation.
     *
     * @param other the other line.
     * @return true if they have the same line equation, false otherwise.
     */
    private boolean haveSameLineEquation(Line other) {
        if (other == null || this.start == null || this.end == null || other.start == null || other.end == null) {
            return false;
        }
        if (this.slope() == null && other.slope() == null) {
            return this.start.getX() == other.start.getX();
        }
        if (this.slope() == null || other.slope() == null) {
            return false;
        }
        // comparing the slope and the intersection with y axis
        boolean isSlopeEqual = Compare.cmpDouble(this.slope(), other.slope());
        boolean isYAxisIntersectionEqual = Compare.cmpDouble(-this.slope() * this.start.getX() + this.start.getY(),
                -other.slope() * other.start.getX() + other.start.getY());
        return isSlopeEqual && isYAxisIntersectionEqual;
    }

    /**
     * A method that checks if the lines contain each other.
     *
     * @param other the other line.
     * @return true if they contain each other, false otherwise.
     */
    public boolean isOn(Line other) {
        if (other == null || this.start == null || this.end == null || other.start == null || other.end == null) {
            return false;
        }
        if (this.haveSameLineEquation(other)) {
            return (this.isPointInRange(other.start) && this.isPointInRange(other.end))
                    || (other.isPointInRange(this.start) && other.isPointInRange(this.end));
        }
        return false;
    }

    /**
     * A method that checks if the current line and another line are intersecting.
     *
     * @param other the line that we want to check if the current line intersects.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // check that there's no null value
        if (other == null || this.start == null || this.end == null || other.start == null || other.end == null) {
            return false;
        }
        // case of two null slopes
        if (this.slope() == null && other.slope() == null) {
            if (!this.haveSameLineEquation(other)) {
                return false;
            } else {
                if (this.isOn(other)) {
                    return Compare.cmpDouble(this.length(), 0.0) || Compare.cmpDouble(other.length(), 0.0);
                } else {
                    return this.start.equals(other.start) || this.end.equals(other.end)
                            || this.start.equals(other.end) || this.end.equals(other.start);
                }
            }
        }
        // case of 'this' having a null slope
        if (this.slope() == null) {
            double intersectionX = this.start.getX(),
                    intersectionY = other.slope() * (intersectionX - other.start.getX()) + other.start.getY();
            Point intersection = new Point(intersectionX, intersectionY);
            // checking if the intersection is on the segments.
            return this.isPointInRange(intersection) && other.isPointInRange(intersection);
        }
        // case of 'other' having a null slope
        if (other.slope() == null) {
            double intersectionX = other.start.getX(),
                    intersectionY = this.slope() * (intersectionX - this.start.getX()) + this.start.getY();
            Point intersection = new Point(intersectionX, intersectionY);
            // checking if the intersection is on the segments.
            return this.isPointInRange(intersection) && other.isPointInRange(intersection);
        }
        // case of different slopes (which are not null)
        if (!(Compare.cmpDouble(this.slope(), other.slope()))) {
            // case of 'this' having a slope which is 0.
            if (Compare.cmpDouble(this.slope(), 0.0)) {
                double intersectionY = this.start.getY(),
                        intersectionX = (intersectionY - other.start.getY()) / other.slope() + other.start.getX();
                Point intersection = new Point(intersectionX, intersectionY);
                // checking if the intersection is on the segments.
                return this.isPointInRange(intersection) && other.isPointInRange(intersection);
            }
            // case of 'other' having a slope which is 0.
            if (Compare.cmpDouble(other.slope(), 0.0)) {
                double intersectionY = other.start.getY(),
                        intersectionX = (intersectionY - this.start.getY()) / this.slope() + this.start.getX();
                Point intersection = new Point(intersectionX, intersectionY);
                // checking if the intersection is on the segments.
                return this.isPointInRange(intersection) && other.isPointInRange(intersection);
            }
            // case of different slopes (which are not null / 0)
            double intersectionX = (this.slope() * this.start.getX() - other.slope() * other.start.getX()
                    + other.start.getY() - this.start.getY()) / (this.slope() - other.slope()),
                    intersectionY = this.slope() * (intersectionX - this.start.getX()) + this.start.getY();
            Point intersection = new Point(intersectionX, intersectionY);
            // checking if the intersection is on the segments.
            return this.isPointInRange(intersection) && other.isPointInRange(intersection);
        }
        // case of equal slopes
        if (!this.haveSameLineEquation(other)) {
            return false;
        } else {
            if (this.isOn(other)) {
                return Compare.cmpDouble(this.length(), 0.0) || Compare.cmpDouble(other.length(), 0.0);
            } else {
                return this.start.equals(other.start) || this.end.equals(other.end)
                        || this.start.equals(other.end) || this.end.equals(other.start);
            }
        }
    }

    /**
     * A method that finds the intersection between the current line and another line.
     *
     * @param other the line that we want to find the intersection of the current line with.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // check that there's no null value.
        if (other == null || this.start == null || this.end == null || other.start == null || other.end == null) {
            return null;
        }
        // check if they are intersecting.
        if (this.isIntersecting(other)) {
            // case of 2 null slopes.
            if (this.slope() == null && other.slope() == null) {
                if (Compare.cmpDouble(this.start.getY(), other.start.getY())) {
                    return this.start;
                }
                if (Compare.cmpDouble(this.end.getY(), other.start.getY())) {
                    return this.end;
                }
                if (Compare.cmpDouble(this.start.getY(), other.end.getY())) {
                    return this.start;
                }
                if (Compare.cmpDouble(this.end.getY(), other.end.getY())) {
                    return this.end;
                }
            }
            // case of 'this' having a null slope.
            if (this.slope() == null) {
                double intersectionX = this.start.getX(),
                        intersectionY = other.slope() * (intersectionX - other.start.getX()) + other.start.getY();
                return new Point(intersectionX, intersectionY);
            }
            // case of 'other' having a null slope.
            if (other.slope() == null) {
                double intersectionX = other.start.getX(),
                        intersectionY = this.slope() * (intersectionX - this.start.getX()) + this.start.getY();
                return new Point(intersectionX, intersectionY);
            }
            // case of 2 equal slopes which are not null
            if (Compare.cmpDouble(other.slope(), this.slope())) {
                if (Compare.cmpDouble(this.length(), 0.0)) {
                    return this.start;
                }
                if (Compare.cmpDouble(other.length(), 0.0)) {
                    return other.start;
                }
                if (this.start.equals(other.start) || this.start.equals(other.end)) {
                    return this.start;
                }
                if (this.end.equals(other.end) || this.end.equals(other.start)) {
                    return this.end;
                }
            }
            //case of 2 different slopes which are not null
            double intersectionX = (this.slope() * this.start.getX() - other.slope() * other.start.getX()
                    + other.start.getY() - this.start.getY()) / (this.slope() - other.slope());
            double intersectionY = this.slope() * (intersectionX - this.start.getX()) + this.start.getY();

            // Changing the values that are not precise.
            if (Compare.cmpDouble(intersectionX, Math.round(intersectionX))) {
                intersectionX = Math.round(intersectionX);
            }
            if (Compare.cmpDouble(intersectionY, Math.round(intersectionY))) {
                intersectionY = Math.round(intersectionY);
            }

            return new Point(intersectionX, intersectionY);
        }
        return null;
    }

    /**
     * A method that checks if the current line is equal to the other line.
     *
     * @param other the line we want to compare the current line to.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end));
    }

    /**
     * If this line does not intersect with the rectangle, return null. Otherwise, return the closest
     * intersection point to the start of the line.
     *
     * @param rect the rectangle.
     * @return null / the closest intersection point to the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        if (rect == null) {
            return null;
        }
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections == null || intersections.isEmpty()) {
            return null;
        }
        Point closestPoint = intersections.get(0);
        for (Point p : intersections) {
            if (p.distance(this.start) < closestPoint.distance(this.start)) {
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    /**
     * A method to calculate the y value of a point using its x value.
     *
     * @param x the point's x value.
     * @return the y value of the point.
     */
    private Double calculateY(double x) {
        if (this.slope() == null) {
            return null;
        }
        if (Compare.cmpDouble(this.slope(), 0.0)) {
            return this.start.getY();
        }
        return this.slope() * (x - this.start.getX()) + this.start.getY();
    }

    /**
     * A method to get a point which is a point on the line, with x and y distance of 1 from the end of the line.
     *
     * @return a point on the line, with x and y distance of 1 from the end of the line.
     */
    public Point getEndCloser() {
        double x = this.end.getX(), y = this.end.getY();
        if (Compare.cmpDouble(this.start.getX(), this.end.getX())) {
            if (this.start.getY() < this.end.getY()) {
                y = this.end.getY() - 0.5;
            } else {
                y = this.end.getY() + 0.5;
            }
        } else if (Compare.cmpDouble(this.start.getY(), this.end.getY())) {
            if (this.start.getX() < this.end.getX()) {
                x = this.end.getX() - 0.5;
            } else {
                x = this.end.getX() + 0.5;
            }
        } else {
            if (this.start.getX() < this.end.getX()) {
                x = this.end.getX() - 0.5;
            } else {
                x = this.end.getX() + 0.5;
            }
            y = this.calculateY(x);
        }
        return new Point(x, y);
    }
}