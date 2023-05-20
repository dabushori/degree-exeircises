// ID - 212945760

/**
 * Line class - A line (actually a line-segment) connects two points -- a start point and an end point.
 * Lines have lengths, and may intersect with other lines. It can also tell if it is the same as another line segment.
 *
 * @author Ori Dabush
 */
public class Line {

    /**
     * epsilon is a const to compare between double numbers.
     */
    private static final double EPSILON = Math.pow(10, -20);

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
        if (cmpDouble(this.start.getX(), this.end.getX())) {
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
        return ((this.start.getX() <= p.getX() && p.getX() <= this.end.getX())
                || (this.end.getX() <= p.getX() && p.getX() <= this.start.getX()))
                && ((this.start.getY() <= p.getY() && p.getY() <= this.end.getY())
                || (this.end.getY() <= p.getY() && p.getY() <= this.start.getY()));
    }

    /**
     * A method that checks if two lines have the same line equation.
     *
     * @param other the other line.
     * @return true if they have the same line equation, false otherwise.
     */
    public boolean haveSameLineEquation(Line other) {
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
        boolean isSlopeEqual = cmpDouble(this.slope(), other.slope());
        boolean isYAxisIntersectionEqual = cmpDouble(-this.slope() * this.start.getX() + this.start.getY(),
                -other.slope() * other.start.getX() + other.start.getY());
        return isSlopeEqual && isYAxisIntersectionEqual;
    }

    /**
     * A method that checks if the lines contain each other.
     *
     * @param other the other line.
     * @return true if they contain each other, false otherwise.
     */
    private boolean isOn(Line other) {
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
                    return cmpDouble(this.length(), 0.0) || cmpDouble(other.length(), 0.0);
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
        if (!(cmpDouble(this.slope(), other.slope()))) {
            // case of 'this' having a slope which is 0.
            if (cmpDouble(this.slope(), 0.0)) {
                double intersectionY = this.start.getY(),
                        intersectionX = (intersectionY - other.start.getY()) / other.slope() + other.start.getX();
                Point intersection = new Point(intersectionX, intersectionY);
                // checking if the intersection is on the segments.
                return this.isPointInRange(intersection) && other.isPointInRange(intersection);
            }
            // case of 'other' having a slope which is 0.
            if (cmpDouble(other.slope(), 0.0)) {
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
                return cmpDouble(this.length(), 0.0) || cmpDouble(other.length(), 0.0);
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
                if (cmpDouble(this.start.getY(), other.start.getY())) {
                    return this.start;
                }
                if (cmpDouble(this.end.getY(), other.start.getY())) {
                    return this.end;
                }
                if (cmpDouble(this.start.getY(), other.end.getY())) {
                    return this.start;
                }
                if (cmpDouble(this.end.getY(), other.end.getY())) {
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
            if (cmpDouble(other.slope(), this.slope())) {
                if (cmpDouble(this.length(), 0.0)) {
                    return this.start;
                }
                if (cmpDouble(other.length(), 0.0)) {
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
                    + other.start.getY() - this.start.getY()) / (this.slope() - other.slope()),
                    intersectionY = this.slope() * (intersectionX - this.start.getX()) + this.start.getY();
            return new Point(intersectionX, intersectionY);
        }
        return null;
    }

    /**
     * A method that checks if the current line is equal to the other line.
     *
     * @param other the line we want to compare the current line to.
     * @return true is the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end));
    }
}