package runtime.models;

import parser.Node;
import runtime.RuntimeVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

// Predefined Functions
public class GlobalFunction  extends Function {

    private final java.util.function.Function<RuntimeVisitor, RuntimeValue> function;

    public GlobalFunction(String name, String[] arguments,java.util.function.Function<RuntimeVisitor, RuntimeValue> function) {
        super(name, null, Arrays.stream(arguments).toList());
        this.function = function;
    }

    @Override
    public RuntimeValue run(RuntimeVisitor visitor) {
        return function.apply(visitor);
    }
}
