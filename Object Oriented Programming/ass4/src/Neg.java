// ID - 212945760

/**
 * This is the Neg class, which describes a neg (-) expression.
 *
 * @author Ori Dabush
 */
public class Neg extends UnaryExpression {
    /**
     * A constructor for the Neg class.
     *
     * @param e the argument of the expression.
     */
    public Neg(Expression e) {
        super(e);
    }

    @Override
    public double operate(double num) {
        return -num;
    }

    @Override
    public String format(String s) {
        return "(-" + s + ")";
    }

    @Override
    public Expression createExpression(Expression e) {
        return new Neg(e);
    }

    @Override
    protected Expression derivative(Expression expression) {
        return new Num(-1);
    }
}