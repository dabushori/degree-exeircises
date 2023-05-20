// ID - 212945760

/**
 * This is the Plus class, which describes a plus expression.
 *
 * @author Ori Dabush
 */
public class Plus extends BinaryExpression {
    /**
     * A constructor for the Plus class.
     *
     * @param left  the left argument of the expression.
     * @param right the right argument of the expression.
     */
    public Plus(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double operate(double num1, double num2) {
        return num1 + num2;
    }

    @Override
    public String format(String s1, String s2) {
        return "(" + s1 + " + " + s2 + ")";
    }

    @Override
    public Expression createExpression(Expression left, Expression right) {
        return new Plus(left, right);
    }

    @Override
    protected Expression derivative(Expression left, Expression right, String var) {
        return new Plus(left.differentiate(var), right.differentiate(var));
    }

    @Override
    protected Expression getSimplified(Expression leftExp, Expression rightExp) {
        Expression simplified = createExpression(leftExp, rightExp);
        if (leftExp.toString().equals(Double.toString(0.0))) {
            simplified = rightExp;
        } else if (rightExp.toString().equals(Double.toString(0.0))) {
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