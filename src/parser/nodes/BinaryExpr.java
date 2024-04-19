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

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "op='" + op + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }


    private boolean isValidForBinaryExpr(LiteralKind type) {
        return type == LiteralKind.INT;
    }

    @Override
    public RuntimeValue execute(ShipVisitor runtime) {
        RuntimeValue leftValue = this.left.execute(runtime);
        RuntimeValue rightValue = this.right.execute(runtime);

        if (!isValidForBinaryExpr(leftValue.getType())) {
            throw new RuntimeException("TypeError: Cannot do binary operations on non numbers. Got " + leftValue.getValue());
        }
        if (!isValidForBinaryExpr(rightValue.getType())) {
            throw new RuntimeException("TypeError: Cannot do binary operations on non numbers. Got " + rightValue.getValue());
        }

        Number value;
        switch (this.op.charAt(0)) {
            case '+' -> {
                value = Integer.parseInt(leftValue.getValue()) + Integer.parseInt(rightValue.getValue());
            }
            case '-' -> {
                value = Integer.parseInt(leftValue.getValue()) - Integer.parseInt(rightValue.getValue());
            }
            case '*' -> {
                value = Integer.parseInt(leftValue.getValue()) * Integer.parseInt(rightValue.getValue());
            }
            default -> {
                value = Integer.parseInt(leftValue.getValue()) / Integer.parseInt(rightValue.getValue());
            }
        }
        return new RuntimeValue(value.toString(), LiteralKind.INT);
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
