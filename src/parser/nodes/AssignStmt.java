package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class AssignStmt extends Node {
    String lhs;
    Node rhs;

    public AssignStmt(String lhs, Node rhs, String loc) {
        super(loc);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getLhs() {
        return lhs;
    }

    public Node getRhs() {
        return rhs;
    }

    @Override
    public String toString() {
        return "AssignStmt{" +
                "lhs='" + lhs + '\'' +
                ", rhs=" + rhs +
                '}';
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
