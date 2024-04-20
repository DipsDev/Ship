package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

import java.util.ArrayList;
import java.util.List;

public class ArrayLit extends Node {

    List<Node> elts;


    public ArrayLit(String loc) {
        super(loc);
        this.elts = new ArrayList<>();
    }

    public void addElement(Node nd) {
        this.elts.add(nd);
    }

    public List<Node> getElements() {
        return elts;
    }

    @Override
    public String toString() {
        return "ArrayLit{" +
                "elts=" + elts +
                '}';
    }

    @Override
    public RuntimeValue<?> accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
