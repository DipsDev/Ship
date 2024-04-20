package runtime.models;

import parser.LiteralKind;

public class RuntimeValue<T> {
    T value;
    LiteralKind type;

    public RuntimeValue(T value, LiteralKind type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public LiteralKind getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RuntimeValue{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
