package runtime.models;

import parser.Node;
import runtime.RuntimeVisitor;

import java.util.ArrayList;
import java.util.List;

import static runtime.RuntimeVisitor.NIL;

public class Function {
    String name;
    List<Node> body;
    List<String> arguments;

    public Function(String name, List<Node> body, List<String> arguments) {
        this.name = name;
        this.body = body;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public RuntimeValue run(RuntimeVisitor visitor) {
        for (Node node : this.body) {
            RuntimeValue val = node.accept(visitor);
            if (val != NIL) {
                return val;
            }
        }
        return NIL;
    }

    public List<String> getArguments() {
        return arguments;
    }
}
