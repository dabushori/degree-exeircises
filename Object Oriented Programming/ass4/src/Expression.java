// ID - 212945760

import java.util.List;
import java.util.Map;

/**
 * This is the Expression interface, which will be implemented by all the expression classes and abstract classes.
 *
 * @author Ori Dabush
 */
public interface Expression {

    /**
     * A method to evaluate the expression using the variable values provided in the assignment.
     *
     * @param assignment is the map of the variables and their values.
     * @return the result of the evaluation.
     * @throws Exception if the expression contains a variable which is not in the assignment
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * A method to evaluate the expression without any variables' values.
     *
     * @return the result of the evaluation.
     * @throws Exception if the expression contains a variable which is not in the assignment
     */
    double evaluate() throws Exception;

    /**
     * A method to get a list of the variables in the expression.
     *
     * @return a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * A method to get a nice string representation of the expression.
     *
     * @return a nice string representation of the expression.
     */
    String toString();

    /**
     * A method to get a new expression in which all occurrences of the given variable are replaced with the given
     * expression (Does not modify the current expression).
     *
     * @param var        is the variable that will be replaced.
     * @param expression the new expression.
     * @return a new expression in which all occurrences of the given variable are replaced with the given expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * A method to get the expression tree resulting from differentiating the current expression relative to variable
     * 'var'.
     *
     * @param var the variable we differentiating by.
     * @return the derivative of the current expression by 'var'
     */
    Expression differentiate(String var);

    /**
     * A method to get a simplified version of the current expression.
     *
     * @return a simplified version of the current expression.
     */
    Expression simplify();
}