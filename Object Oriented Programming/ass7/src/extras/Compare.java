// ID - 212945760

package extras;

/**
 * This is the compare class, which will be used to compare between 2 double variables.
 *
 * @author Ori Dabush.
 */
public class Compare {
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
    public static boolean cmpDouble(double d1, double d2) {
        return Math.abs(d1 - d2) <= EPSILON;
    }
}