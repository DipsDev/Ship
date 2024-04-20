package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RuntimeValue;

public class StringValue extends RuntimeValue<String> {
    public StringValue(String value, LiteralKind type) {
        super(value, type);
    }

    @Override
    public String getPrintable() {
        return value;
    }
}
