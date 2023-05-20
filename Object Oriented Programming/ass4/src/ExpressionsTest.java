// ID - 212945760

import java.util.Map;
import java.util.TreeMap;

/**
 * This is the ExpressionsTest class, which will operate the wanted main function.
 *
 * @author Ori Dabush
 */
public class ExpressionsTest {
    /**
     * The main function doing the wanted things.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        // (2x) + (sin(4y)) + (e^x)
        Expression e = new Plus(
                new Mult(
                        new Num(2),
                        new Var("x")),
                new Plus(
                        new Sin(
                                new Mult(
                                        new Num(4),
                                        new Var("y"))),
                        new Pow(
                                new Var("e"),
                                new Var("x"))));
        System.out.println(e);
        Map<String, Double> assignment = new TreeMap<>();
        assignment.put("x", 2.0);
        assignment.put("y", 0.25);
        assignment.put("e", 2.71);
        try {
            System.out.println(e.evaluate(assignment));
        } catch (Exception ex) {
            // do nothing because you shouldn't be here
            System.out.print("");
        }
        Expression de = e.differentiate("x");
        System.out.println(de);
        try {
            System.out.println(de.evaluate(assignment));
        } catch (Exception ex) {
            // do nothing because you shouldn't be here
            System.out.print("");
        }
        System.out.println(de.simplify());
    }
}