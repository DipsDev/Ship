package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class BasicLit extends Node {

    String value;
    LiteralKind kind;

    public BasicLit(String value, LiteralKind kind, String loc) {
        super(loc);
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
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
