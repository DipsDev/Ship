package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

import java.util.ArrayList;

public class IfStmt extends Node {
    Node expr;
    ArrayList<Node> body;

    public IfStmt(Node expr, ArrayList<Node> body) {
        this.expr = expr;
        this.body = body;
    }

    public ArrayList<Node> getBody() {
        return body;
    }

    public Node getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "IfStmt{" +
                "expr=" + expr +
                ", body=" + body +
                '}';
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
