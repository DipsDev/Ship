package parser.nodes;

import lexer.LexerQueue;
import lexer.Token;
import parser.Node;
import parser.TypedNode;

public class BinaryExpr extends Node implements TypedNode {
    private String op;

    private Node left;
    private Node right;


    public BinaryExpr(String op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public String getOp() {
        return op;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
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
    public LiteralKind getType() {
        return LiteralKind.INT;
    }
}
