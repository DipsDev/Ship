package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class DeclStmt extends Node {

    Node value;
    String tok;
    String name;

    public DeclStmt(Node value, String tok, String name) {
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

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
