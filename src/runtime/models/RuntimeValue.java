package runtime.models;

import parser.nodes.LiteralKind;

public class RuntimeValue {
    String value;
    LiteralKind type;

    public RuntimeValue(String value, LiteralKind type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
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
