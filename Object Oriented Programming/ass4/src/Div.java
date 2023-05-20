// ID - 212945760

/**
 * This is the Div class, which describes a div expression.
 *
 * @author Ori Dabush
 */
public class Div extends BinaryExpression {
    /**
     * A constructor for the Div class.
     *
     * @param left  the left argument of the div expression.
     * @param right the right argument of the div expression.
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected void check(double num1, double num2) throws Exception {
        if (Compare.doubleNum(num2, 0.0)) {
            throw new Exception("cant divide by zero");
        }
    }

    @Override
    public double operate(double num1, double num2) {
        return num1 / num2;
    }

    @Override
    public String format(String s1, String s2) {
        return "(" + s1 + " / " + s2 + ")";
    }

    @Override
    public Expression createExpression(Expression left, Expression right) {
        return new Div(left, right);
    }

    @Override
    protected Expression derivative(Expression left, Expression right, String var) {
        return new Div(
                new Minus(
                        new Mult(left.differentiate(var), right),
                        new Mult(left, right.differentiate(var))),
                new Pow(right, new Num(2)));
    }

    @Override
    protected Expression getSimplified(Expression leftExp, Expression rightExp) {
        Expression simplified = createExpression(leftExp, rightExp);
        if (leftExp.toString().equals(rightExp.toString())) {
            simplified = new Num(1);
        }
        if (rightExp.toString().equals(Double.toString(1.0))) {
            simplified = leftExp;
        }
        try {
            double evaluated = simplified.evaluate();
            simplified = new Num(evaluated);
        } catch (Exception e) {
            // do nothing
            System.out.print("");
        }
        return simplified;
    }
}