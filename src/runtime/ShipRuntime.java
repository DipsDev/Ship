package runtime;

import errors.ShipTypeError;
import parser.BaseKind;
import parser.Node;
import parser.LiteralKind;
import parser.nodes.Program;
import runtime.models.GlobalFunction;
import runtime.models.RuntimeValue;
import runtime.models.Variable;
import runtime.models.values.NumberValue;
import runtime.models.values.StringValue;

import java.util.HashMap;
import java.util.List;
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
            System.out.println(visitor.getVariable("object").getValue().getPrintable());
            return RuntimeVisitor.NIL;
        }));
    }

    private GlobalFunction createTimeFunction() {
        return new GlobalFunction("time", new String[] {}, (visitor -> {
            return new NumberValue(((Number) System.nanoTime()).intValue(), LiteralKind.INT);
        }));
    }

    private GlobalFunction createSizeFunction() {
        return new GlobalFunction("size", new String[] {"object"}, (visitor -> {
            Variable var = visitor.getVariable("object");
            if (var.getValue().getType().getBase() != BaseKind.ARRAY) {
                throw new ShipTypeError(String.format("object of type '%s' has no size()", var.getValue().getType().name().toLowerCase(Locale.ROOT)), var.getValue().getValue().toString(), "");
            }
            if (var.getValue().getType() == LiteralKind.STRING) {
                String value = (String) var.getValue().getValue();
                return new NumberValue(value.length(), LiteralKind.INT);
            }
            List<RuntimeValue<?>> values = (List<RuntimeValue<?>>) var.getValue().getValue();
            return new NumberValue(values.size(), LiteralKind.INT);
        }));
    }

    private GlobalFunction createConcatFunction() {
        return new GlobalFunction("paste", new String[] {"object", "object2"}, (visitor -> {
            Variable object = visitor.getVariable("object");
            Variable object2 = visitor.getVariable("object2");

            if (object.getValue().getType() == LiteralKind.ARRAY || object2.getValue().getType() == LiteralKind.ARRAY) {
                throw new ShipTypeError("object of type array cannot be concatenated with other types", object.getValue().getValue().toString(), "");
            }

            return new StringValue(object.getValue().getValue().toString() + object2.getValue().getValue().toString(), LiteralKind.STRING);
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
