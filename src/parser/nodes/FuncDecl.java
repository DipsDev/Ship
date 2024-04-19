package parser.nodes;

import parser.Node;

import java.util.ArrayList;

public class FuncDecl extends Node {
    ArrayList<Node> body;
    ArrayList<FuncParam> params;

    String name;

    public FuncDecl(String name) {
        this.body = new ArrayList<>();
        this.params = new ArrayList<>();
        this.name = name;
    }

    public void appendBody(Node nd) {
        this.body.add(nd);
    }

    public void appendParam(FuncParam nd) {
        this.params.add(nd);
    }

    public ArrayList<Node> getBody() {
        return body;
    }

    public ArrayList<FuncParam> getParams() {
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
}
