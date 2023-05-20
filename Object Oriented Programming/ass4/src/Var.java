// ID - 212945760

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is the Var class, which describes a variable expression.
 *
 * @author Ori Dabush
 */
public class Var implements Expression {
    /**
     * The variable's name.
     */
    private String name;

    /**
     * A constructor for the Var class.
     *
     * @param name the variable's name.
     */
    public Var(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            throw new Exception("var can't be evaluated");
        }
        return assignment.get(this.name);
    }

    @Override
    public double evaluate() throws Exception {
        throw new Exception("var can't be evaluated");
    }

    @Override
    public List<String> getVariables() {
        List<String> vars = new LinkedList<>();
        vars.add(this.name);
        return vars;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (this.name.equals(var)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression differentiate(String var) {
        if (this.name.equals(var)) {
            return new Num(1);
        }
        return new Num(0);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}