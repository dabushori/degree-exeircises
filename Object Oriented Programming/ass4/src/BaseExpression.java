// ID - 212945760

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is the BaseExpression abstract class, which describes an expression and used to share code.
 *
 * @author Ori Dabush
 */
public abstract class BaseExpression implements Expression {

    @Override
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;

    @Override
    public double evaluate() throws Exception {
        return evaluate(new TreeMap<String, Double>());
    }

    /**
     * A protected method to get the arguments of the expression.
     *
     * @return an array with the arguments (the length of the array is 1 or 2).
     */
    protected abstract Expression[] getExpressions();

    @Override
    public List<String> getVariables() {
        List<String> vars = new LinkedList<>();
        Expression[] expressions = getExpressions();
        for (Expression e : expressions) {
            List<String> eVars = e.getVariables();
            for (String var : eVars) {
                if (!vars.contains(var)) {
                    vars.add(var);
                }
            }
        }
        return vars;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract Expression assign(String var, Expression expression);

    @Override
    public abstract Expression differentiate(String var);

    @Override
    public abstract Expression simplify();
}