package parser.nodes;

import parser.Node;

public class Ident extends Node {
    String name;

    public Ident(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ident{" +
                "name='" + name + '\'' +
                '}';
    }
}
