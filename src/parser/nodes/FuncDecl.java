package parser.nodes;

import parser.Node;
import runtime.RuntimeVisitor;
import runtime.models.RuntimeValue;

import java.util.ArrayList;

public class FuncDecl extends Node {
    ArrayList<Node> body;
    ArrayList<String> params;

    String name;

    public FuncDecl(String name, String loc) {
        super(loc);
        this.body = new ArrayList<>();
        this.params = new ArrayList<>();
        this.name = name;
    }

    public void appendBody(Node nd) {
        this.body.add(nd);
    }

    public void appendParam(String nd) {
        this.params.add(nd);
    }

    public ArrayList<Node> getBody() {
        return body;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FuncDecl{" +
                "body=" + body +
                ", params=" + params +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public RuntimeValue<?> accept(RuntimeVisitor runtime) {
        return runtime.visit(this);
    }
}
