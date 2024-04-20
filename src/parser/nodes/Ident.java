package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public class Ident extends Node {
    String name;

    public Ident(String name, String loc) {
        super(loc);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ident{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    @Override
    public RuntimeValue<?> accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
