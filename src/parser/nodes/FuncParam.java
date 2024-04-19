package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

public class FuncParam extends Node {

    String name;

    public FuncParam(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FuncParam{" +
                "name='" + name + '\'' +
                '}';
    }
    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
