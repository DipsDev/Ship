package runtime;

import errors.ShipTypeError;
import parser.Node;
import parser.nodes.LiteralKind;
import parser.nodes.Program;
import runtime.models.GlobalFunction;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public class ShipRuntime {
    ShipVisitor main;

    public ShipRuntime() {
        this.main = new ShipVisitor();

        this.main.createFunction(createPrintFunction());
        this.main.createFunction(createConcatFunction());
    }

    private GlobalFunction createPrintFunction() {
        return new GlobalFunction("puts", new String[] {"object"}, (visitor -> {
            System.out.println(visitor.getVariable("object").getValue().getValue());
            return RuntimeVisitor.NIL;
        }));
    }

    private GlobalFunction createConcatFunction() {
        return new GlobalFunction("concat", new String[] {"object", "object2"}, (visitor -> {
            Variable object = visitor.getVariable("object");
            Variable object2 = visitor.getVariable("object2");
            if (object2.getValue().getType() != LiteralKind.STRING) {
                throw new ShipTypeError("concat receives 'string' types.", object2.getName());
            }
            if (object.getValue().getType() != LiteralKind.STRING) {
                throw new ShipTypeError("concat receives 'string' types.", object.getName());
            }

            return new RuntimeValue(object.getValue().getValue() + object2.getValue().getValue(), LiteralKind.STRING);
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
