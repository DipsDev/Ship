package parser.nodes;

import parser.Node;

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
}
