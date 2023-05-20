// ID - 212945760

/**
 * The velocity class. Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ori Dabush
 */
public class Velocity {

    /**
     * The velocity parameters - dx and dy.
     */
    private double dx;
    private double dy;

    /**
     * A constructor that creates a velocity object from dx and dy.
     *
     * @param dx the change in x axis.
     * @param dy the change in y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * A constructor that creates a velocity object from another velocity - by copying its values.
     *
     * @param v the other velocity.
     */
    public Velocity(Velocity v) {
        this.dx = v.dx;
        this.dy = v.dy;
    }

    /**
     * A method that creates velocity using angle and speed.
     *
     * @param angle the angle (in degrees) of the movement line (while assuming up's angle is 0).
     * @param speed the speed value.
     * @return the velocity object.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = 0, dy = 0;
        if (angle % 90 == 0) {
            if ((angle / 90) % 4 == 0) {
                dx = 0;
                dy = -speed;
            } else if ((angle / 90) % 4 == 1) {
                dx = speed;
                dy = 0;
            } else if ((angle / 90) % 4 == 2) {
                dx = 0;
                dy = speed;
            } else if ((angle / 90) % 4 == 3) {
                dx = -speed;
                dy = 0;
            }
        } else {
            angle = Math.toRadians(angle);
            dx = speed * Math.sin(angle);
            dy = -1 * speed * Math.cos(angle); //because dy's direction is down
        }
        return new Velocity(dx, dy);
    }

    /**
     * An accessor for the dx value of the velocity.
     *
     * @return the dx value of the velocity.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * An accessor for the dy value of the velocity.
     *
     * @return the dy value of the velocity.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * A method that takes a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the point we want to change.
     * @return the new point.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}