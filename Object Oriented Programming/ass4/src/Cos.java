// ID - 212945760

/**
 * This is the Cos class, which describes a cosine expression.
 *
 * @author Ori Dabush
 */
public class Cos extends UnaryExpression {
    /**
     * A constructor for the Cos class.
     *
     * @param e the argument we using cosine on.
     */
    public Cos(Expression e) {
        super(e);
    }

    @Override
    public double operate(double num) {
        return Math.cos(Math.toRadians(num));
    }

    @Override
    public String format(String s) {
        return "cos(" + s + ")";
    }

    @Override
    public Expression createExpression(Expression e) {
        return new Cos(e);
    }

    @Override
    protected Expression derivative(Expression expression) {
        return new Neg(new Sin(expression));
    }
}