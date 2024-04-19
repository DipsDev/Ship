package runtime;

import parser.Node;
import parser.nodes.Program;
import runtime.models.GlobalFunction;
import runtime.models.Variable;

import java.util.HashMap;

public class ShipRuntime {
    ShipVisitor main;

    public ShipRuntime() {
        this.main = new ShipVisitor();

        this.main.createFunction(createPrintFunction());
    }

    private GlobalFunction createPrintFunction() {
        return new GlobalFunction("puts", new String[] {"object"}, (visitor -> {
            System.out.println(visitor.getVariable("object").getValue().getValue());
            return RuntimeVisitor.NIL;
        }));
    }



    public void execute(Program program) {
        for (Node nd : program.getBody()) {
            nd.accept(this.main);
        }
    }

    public HashMap<String, Variable> getVariables() {
        return this.main.variables;
    }
}
