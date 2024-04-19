package parser.nodes;

import parser.Node;
import parser.TypedNode;

public class BasicLit extends Node implements TypedNode {

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

    @Override
    public LiteralKind getType() {
        return kind;
    }
}
