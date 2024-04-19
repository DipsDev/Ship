package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

import java.util.ArrayList;

public class CallExpr extends Node {
    String name;
    ArrayList<Node> params;

    public CallExpr(String name) {
        this.name = name;
        this.params = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "CallExpr{" +
                "name='" + name + '\'' +
                ", params=" + params +
                '}';
    }

    public void addParam(Node nd) {
        this.params.add(nd);
    }

    @Override
    public RuntimeValue accept(RuntimeVisitor runtime) {

        return runtime.visit(this);
    }
}
