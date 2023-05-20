// ID - 212945760

import java.util.Map;

/**
 * This is the UnaryExpression abstract class, which describes an expression with one argument.
 *
 * @author Ori Dabush
 */
public abstract class UnaryExpression extends BaseExpression {
    /**
     * The argument of the expression.
     */
    private Expression argument;

    /**
     * A constructor for the UnaryExpression class.
     *
     * @param argument the argument of the expression.
     */
    public UnaryExpression(Expression argument) {
        super();
        this.argument = argument;
    }

    @Override
    protected Expression[] getExpressions() {
        Expression[] expressions = new Expression[1];
        expressions[0] = this.argument;
        return expressions;
    }

    /**
     * An abstract method to operate the current expression with one argument. This method will be implemented by every
     * subclass of the current class.
     *
     * @param num the argument of the expression.
     * @return the result of the expression using the given argument.
     */
    protected abstract double operate(double num);

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double result = operate(this.argument.evaluate(assignment));
        return result;
    }

    /**
     * An abstract method to create the toString of the expression using the toString of the argument of the
     * expression. This method will be implemented by every subclass of the current class.
     *
     * @param s the argument's toString.
     * @return the toString of the expression using the toStrings of the argument.
     */
    protected abstract String format(String s);

    @Override
    public String toString() {
        return format(this.argument.toString());
    }

    /**
     * A abstract method to create an expression using a given expression as argument. This method will be
     * implemented by every subclass of the current class.
     *
     * @param e the argument of the expression.
     * @return the new expression that was created.
     */
    protected abstract Expression createExpression(Expression e);

    @Override
    public Expression assign(String var, Expression expression) {
        return createExpression(this.argument.assign(var, expression));
    }

    /**
     * An abstract method to get the derivative of the current expression by a given variable. It doesn't calculate's
     * the whole derivative, just the derivative of the expression with the same argument.
     *
     * @param expression the argument of the expression.
     * @return an expression of the derivative.
     */
    protected abstract Expression derivative(Expression expression);

    @Override
    public Expression differentiate(String var) {
        return new Mult(derivative(this.argument), this.argument.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression simplified = createExpression(this.argument.simplify());
        try {
            simplified = new Num(simplified.evaluate());
        } catch (Exception e) {
            // do nothing
            System.out.print("");
        }
        return simplified;
    }
}