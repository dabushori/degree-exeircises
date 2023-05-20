// ID - 212945760

import java.util.Map;

/**
 * This is the BinaryExpression abstract class, which describes an expression with two arguments.
 *
 * @author Ori Dabush
 */
public abstract class BinaryExpression extends BaseExpression {
    /**
     * The arguments of the expression.
     */
    private Expression left;
    private Expression right;

    /**
     * A constructor for the BinaryExpression abstract class.
     *
     * @param left  the left argument of the expression.
     * @param right the right argument of the expression.
     */
    public BinaryExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    protected Expression[] getExpressions() {
        Expression[] expressions = new Expression[2];
        expressions[0] = this.left;
        expressions[1] = this.right;
        return expressions;
    }

    /**
     * A method that checks if two arguments can be in the expression. This method will be implemented again by every
     * subclass of the current class which needs to do a special check.
     *
     * @param num1 the left argument of the expression.
     * @param num2 the right argument of the expression.
     * @throws Exception if the arguments can't be in the expression.
     */
    protected void check(double num1, double num2) throws Exception {
        // doing absolutely nothing, a default implementation for the method.
    }

    /**
     * An abstract method to operate the current expression with two arguments. This method will be implemented by
     * every subclass of the current class.
     *
     * @param num1 the left argument of the expression.
     * @param num2 the right argument of the expression.
     * @return the result of the expression using the given arguments.
     */
    protected abstract double operate(double num1, double num2);

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftEvaluated = this.left.evaluate(assignment), rightEvaluated = this.right.evaluate(assignment);
        check(leftEvaluated, rightEvaluated);
        double result = operate(leftEvaluated, rightEvaluated);
        return result;
    }

    /**
     * An abstract method to create the toString of the expression using two toStrings of the arguments of the
     * expression. This method will be implemented by every subclass of the current class.
     *
     * @param s1 the left argument's toString.
     * @param s2 the right argument's toString.
     * @return the toString of the expression using the toStrings of the arguments.
     */
    protected abstract String format(String s1, String s2);

    @Override
    public String toString() {
        return format(this.left.toString(), this.right.toString());
    }

    /**
     * A abstract method to create an expression using two given expressions as arguments. This method will be
     * implemented by every subclass of the current class.
     *
     * @param leftExp  the left argument.
     * @param rightExp the right argument.
     * @return the new expression that was created.
     */
    protected abstract Expression createExpression(Expression leftExp, Expression rightExp);

    @Override
    public Expression assign(String var, Expression expression) {
        return createExpression(this.left.assign(var, expression), this.right.assign(var, expression));
    }

    /**
     * An abstract method to get the derivative of the current expression by a given variable.
     *
     * @param leftExp  the left argument.
     * @param rightExp the right argument.
     * @param var      the variable used to calculate the derivative.
     * @return an expression of the derivative.
     */
    protected abstract Expression derivative(Expression leftExp, Expression rightExp, String var);

    @Override
    public Expression differentiate(String var) {
        return derivative(this.left, this.right, var);
    }

    /**
     * A method to simplify the current expression with two given arguments, assuming the arguments are simplified.
     * This method will be implemented again by every subclass that have any other simplification except evaluating
     * the expression.
     *
     * @param leftExp  the left argument which is simplified.
     * @param rightExp the right argument which is simplified.
     * @return the simplified expression.
     */
    protected Expression getSimplified(Expression leftExp, Expression rightExp) {
        Expression simplified = createExpression(leftExp, rightExp);
        try {
            return new Num(simplified.evaluate());
        } catch (Exception e) {
            return simplified;
        }
    }

    @Override
    public Expression simplify() {
        Expression leftSimplified = this.left.simplify(), rightSimplified = this.right.simplify();
        return getSimplified(leftSimplified, rightSimplified);
    }
}