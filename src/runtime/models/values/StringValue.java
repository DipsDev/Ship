package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RandomAccessValue;
import runtime.models.RuntimeValue;

public class StringValue extends RuntimeValue<String> implements RandomAccessValue {
    public StringValue(String value, LiteralKind type) {
        super(value, type);
    }

    public StringValue(String value) {
        super(value, LiteralKind.STRING);
    }

    @Override
    public String getPrintable() {
        return value;
    }

    @Override
    public RuntimeValue<?> getAt(int index) {
        return new StringValue(value.charAt(index) + "");
    }
}
