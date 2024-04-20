package runtime.models;

import errors.ShipTypeError;
import parser.LiteralKind;

public class RuntimeValue<T> {
    protected T value;
    protected LiteralKind type;

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

    public String getPrintable() {
        throw new ShipTypeError("Unable to print type", value.toString(), "");
    }

    @Override
    public String toString() {
        return "RuntimeValue{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
