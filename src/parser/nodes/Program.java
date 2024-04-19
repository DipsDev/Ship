package parser.nodes;

import parser.Node;

import java.util.ArrayList;

public class Program extends Node {
    ArrayList<Node> body;

    public Program() {
        super("");
        body = new ArrayList<>();
    }

    public void add(Node nd) {
        body.add(nd);
    }

    public ArrayList<Node> getBody() {
        return body;
    }
}
