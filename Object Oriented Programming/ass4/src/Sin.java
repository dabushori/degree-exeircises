// ID - 212945760

/**
 * This is the Sin class, which describes a sine expression.
 *
 * @author Ori Dabush
 */
public class Sin extends UnaryExpression {
    /**
     * A constructor for the Sin class.
     *
     * @param e the argument of the expression.
     */
    public Sin(Expression e) {
        super(e);
    }

    @Override
    public double operate(double num) {
        return Math.sin(Math.toRadians(num));
    }

    @Override
    protected String format(String s) {
        return "sin(" + s + ")";
    }

    @Override
    protected Expression createExpression(Expression e) {
        return new Sin(e);
    }

    @Override
    protected Expression derivative(Expression expression) {
        return new Cos(expression);
    }
}
