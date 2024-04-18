package parser.nodes;

import parser.Node;

public class FuncParam extends Node {

    String type;
    String name;

    public FuncParam(String type, String name) {
        this.type = type;
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FuncParam{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
