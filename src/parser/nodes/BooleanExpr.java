package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

public class BooleanExpr extends Node {
    private String op;

    private Node left;
    private Node right;

    public BooleanExpr(String op, Node left, Node right, String loc) {
        super(loc);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "BooleanExpr{" +
                "op='" + op + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public RuntimeValue<?> accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
