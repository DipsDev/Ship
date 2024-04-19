package parser.nodes;

import parser.Node;

import java.util.ArrayList;

public class CallExpr extends Node {
    String name;
    ArrayList<Node> params;

    public CallExpr(String name) {
        this.name = name;
        this.params = new ArrayList<>();
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
}
