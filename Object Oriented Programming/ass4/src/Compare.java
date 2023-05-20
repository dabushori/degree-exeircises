// ID - 212945760

/**
 * This is the compare class which will be used to compare double numbers.
 *
 * @author Ori Dabush
 */
public class Compare {
    /**
     * A very small constant which will be used to compare the doubles.
     */
    private static final double EPSILON = Math.pow(10, -8);

    /**
     * A method to compare between two doubles.
     *
     * @param d1 the first double.
     * @param d2 the second double.
     * @return true if they are equal, false otherwise.
     */
    public static boolean doubleNum(double d1, double d2) {
        return Math.abs(d1 - d2) <= EPSILON;
    }
}
