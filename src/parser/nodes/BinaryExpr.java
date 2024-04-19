package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class BinaryExpr extends Node {
    private String op;

    private Node left;
    private Node right;

    public BinaryExpr(String op, Node left, Node right) {
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
        return "BinaryExpression{" +
                "op='" + op + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
