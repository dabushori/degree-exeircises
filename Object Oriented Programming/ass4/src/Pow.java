// ID - 212945760

/**
 * This is the Pow class, which describes a pow expression.
 *
 * @author Ori Dabush
 */
public class Pow extends BinaryExpression {
    /**
     * A constructor for the Pow class.
     *
     * @param left  the left argument of the expression.
     * @param right the right argument of the expression.
     */
    public Pow(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected void check(double num1, double num2) throws Exception {
        if (Compare.doubleNum(num1, 0.0) && Compare.doubleNum(num2, 0.0)) {
            throw new Exception("can't calculate 0^0");
        }
        if (Double.isNaN(Math.pow(num1, num2))) {
            throw new Exception("can't calculate " + num1 + "^" + num2);
        }
    }

    @Override
    public double operate(double num1, double num2) {
        return Math.pow(num1, num2);
    }

    @Override
    public String format(String s1, String s2) {
        return "(" + s1 + "^" + s2 + ")";
    }

    @Override
    public Expression createExpression(Expression left, Expression right) {
        return new Pow(left, right);
    }

    @Override
    protected Expression derivative(Expression left, Expression right, String var) {
        return new Mult(
                new Pow(left, right),
                new Plus(
                        new Mult(
                                left.differentiate(var),
                                new Div(
                                        right,
                                        left)),
                        new Mult(
                                right.differentiate(var),
                                new Log(
                                        new Var("e"),
                                        left))));
    }
}