package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

public class UnaryExpr extends Node {

    char sign;
    Node value;

    public UnaryExpr(char sign, Node value, String loc) {
        super(loc);
        this.sign = sign;
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    public char getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "UnaryExpr{" +
                "sign=" + sign +
                ", value=" + value +
                '}';
    }

    @Override
    public RuntimeValue<?> accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
