// ID - 212945760

/**
 * This is the Log class, which describes a log expression.
 *
 * @author Ori Dabush
 */
public class Log extends BinaryExpression {
    /**
     * A constructor for the Log class.
     *
     * @param left  the left argument of the expression.
     * @param right the right argument of the expression.
     */
    public Log(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected void check(double num1, double num2) throws Exception {
        if (num1 < 0.0 || Compare.doubleNum(num1, 0.0) || Compare.doubleNum(num1, 1.0)) {
            throw new Exception("base of the log can't be negative or equal to zero and one");
        }
        if (num2 < 0.0 || Compare.doubleNum(num2, 0.0)) {
            throw new Exception("the expression inside the log can't be negative or equal to zero");
        }
    }

    @Override
    public double operate(double num1, double num2) {
        return Math.log(num2) / Math.log(num1);
    }

    @Override
    public String format(String s1, String s2) {
        return "log(" + s1 + ", " + s2 + ")";
    }

    @Override
    public Expression createExpression(Expression left, Expression right) {
        return new Log(left, right);
    }

    @Override
    protected Expression derivative(Expression left, Expression right, String var) {
        return new Div(
                right.differentiate(var),
                new Mult(right, new Log(new Var("e"), left)));
    }

    @Override
    protected Expression getSimplified(Expression leftExp, Expression rightExp) {
        Expression simplified = createExpression(leftExp, rightExp);
        if (leftExp.toString().equals(rightExp.toString())) {
            simplified = new Num(1);
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