// ID - 212945760

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is the Num class, which describes a numeric expression.
 */
public class Num implements Expression {
    /**
     * The expression's value.
     */
    private double value;

    /**
     * A constructor for the Num class.
     *
     * @param value is the expression's value.
     */
    public Num(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.value;
    }

    @Override
    public double evaluate() throws Exception {
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        return new LinkedList<String>();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}