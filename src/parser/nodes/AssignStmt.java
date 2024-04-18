package parser.nodes;

import parser.Node;

public class AssignStmt extends Node {
    String lhs;
    Node rhs;

    public AssignStmt(String lhs, Node rhs) {
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
}
