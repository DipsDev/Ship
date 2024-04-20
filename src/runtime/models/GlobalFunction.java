package runtime.models;

import runtime.RuntimeVisitor;

import java.util.Arrays;

// Predefined Functions
public class GlobalFunction  extends Function {

    private final java.util.function.Function<RuntimeVisitor, RuntimeValue<?>> function;

    public GlobalFunction(String name, String[] arguments,java.util.function.Function<RuntimeVisitor, RuntimeValue<?>> function) {
        super(name, null, Arrays.stream(arguments).toList());
        this.function = function;
    }

    @Override
    public RuntimeValue<?> run(RuntimeVisitor visitor) {
        return function.apply(visitor);
    }
}
