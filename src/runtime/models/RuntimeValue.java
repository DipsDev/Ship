package runtime.models;

import errors.ShipError;
import errors.ShipTypeError;
import parser.BaseKind;
import parser.LiteralKind;

public class RuntimeValue<T> {
    protected T value;
    protected LiteralKind type;

    public RuntimeValue(T value, LiteralKind type) {
        this.value = value;
        this.type = type;
    }

    public void validate(LiteralKind type, ShipError error) {
        if (type != this.type) {
            throw error;
        }
    }

    public void validateBase(BaseKind type, ShipError error) {
        if (type != this.type.getBase()) {
            throw error;
        }
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
