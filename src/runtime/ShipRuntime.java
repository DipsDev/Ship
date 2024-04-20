package runtime;

import errors.ShipTypeError;
import parser.BaseKind;
import parser.Node;
import parser.LiteralKind;
import parser.nodes.Program;
import runtime.models.GlobalFunction;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;
import java.util.Locale;

public class ShipRuntime {
    ShipVisitor main;

    public ShipRuntime() {
        this.main = new ShipVisitor();

        this.main.createFunction(createPrintFunction());
        this.main.createFunction(createConcatFunction());
        this.main.createFunction(createTimeFunction());
        this.main.createFunction(createSizeFunction());
    }

    private GlobalFunction createPrintFunction() {
        return new GlobalFunction("puts", new String[] {"object"}, (visitor -> {
            System.out.println(visitor.getVariable("object").getValue().getValue());
            return RuntimeVisitor.NIL;
        }));
    }

    private GlobalFunction createTimeFunction() {
        return new GlobalFunction("time", new String[] {}, (visitor -> {
            return new RuntimeValue(((Number) System.nanoTime()).intValue() + "", LiteralKind.INT);
        }));
    }

    private GlobalFunction createSizeFunction() {
        return new GlobalFunction("size", new String[] {"object"}, (visitor -> {
            Variable var = visitor.getVariable("object");
            if (var.getValue().getType().getBase() != BaseKind.ARRAY) {
                throw new ShipTypeError(String.format("object of type '%s' has no size()", var.getValue().getType().name().toLowerCase(Locale.ROOT)), var.getValue().getValue(), "");
            }

            return new RuntimeValue(var.getValue().getValue().length() + "", LiteralKind.INT);
        }));
    }

    private GlobalFunction createConcatFunction() {
        return new GlobalFunction("paste", new String[] {"object", "object2"}, (visitor -> {
            Variable object = visitor.getVariable("object");
            Variable object2 = visitor.getVariable("object2");

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
