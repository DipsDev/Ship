package runtime.models;

import parser.Node;

import java.util.ArrayList;

public class Function {
    String name;
    ArrayList<Node> body;
    ArrayList<String> arguments;

    public Function(String name, ArrayList<Node> body, ArrayList<String> arguments) {
        this.name = name;
        this.body = body;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getBody() {
        return body;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }
}
