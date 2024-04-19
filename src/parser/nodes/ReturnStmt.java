package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

public class ReturnStmt extends Node {
    Node result;

    public ReturnStmt(Node result, String loc) {
        super(loc);
        this.result = result;
    }

    public Node getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ReturnStmt{" +
                "result=" + result +
                '}';
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
