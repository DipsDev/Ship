package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class DeclStmt extends Node {

    Node value;
    String tok;
    String name;

    public DeclStmt(Node value, String tok, String name, String loc) {
        super(loc);
        this.value = value;
        this.tok = tok;
        this.name = name;
    }


    @Override
    public String toString() {
        return "DeclStmt{" +
                "value=" + value +
                ", tok='" + tok + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }

    public String getTok() {
        return tok;
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
