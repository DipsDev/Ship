package parser.nodes;

import parser.Node;

public class BasicLit extends Node {

    String value;
    LiteralKind kind;

    public BasicLit(String value, LiteralKind kind) {
        this.value = value;
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BasicLit{" +
                "value='" + value + '\'' +
                ", kind=" + kind +
                '}';
    }

    public LiteralKind getKind() {
        return kind;
    }
}
