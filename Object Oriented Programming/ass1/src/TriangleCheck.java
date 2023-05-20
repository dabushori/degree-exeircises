// ID: 212945760

/**
 * A class which checks if 3 edges can make a triangle.
 *
 * @author Ori Dabush
 */
public class TriangleCheck {

    /**
     * The isTriangle function which checks if a, b and c can represent the lengths of edges of a triangle.
     *
     * @param a length of the first edge.
     * @param b length of the second edge.
     * @param c length of the third edge.
     * @return true/false - if they can/can't represent the lengths of edges of a triangle.
     */
    static boolean isTriangle(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    /**
     * The isRightAngle function which checks if there's a right angle in the triangle using pythagoras theorem.
     *
     * @param a length of the first edge.
     * @param b length of the second edge.
     * @param c length of the third edge.
     * @return true/false - if they have/don't have a right angle.
     */
    static boolean isRightAngle(double a, double b, double c) {
        return (a * a + b * b == c * c) || (b * b + c * c == a * a) || (a * a + c * c == b * b);
    }

    /**
     * The main function which checks the input and operate the prints the right output for it.
     *
     * @param args is the arguments passed to the main function in the command line.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid input");
        } else {
            double a = Double.parseDouble(args[0]), b = Double.parseDouble(args[1]), c = Double.parseDouble(args[2]);
            if (!(a > 0 && b > 0 && c > 0)) {
                System.out.println("Invalid input");
            } else {
                if (isTriangle(a, b, c)) {
                    System.out.println("triangle!");
                    if (isRightAngle(a, b, c)) {
                        System.out.println("right angled!");
                    }
                } else {
                    System.out.println("not triangle!");
                }
            }
        }
    }
}
