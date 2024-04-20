package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RuntimeValue;

public class BooleanValue extends RuntimeValue<Boolean> {
    public BooleanValue(Boolean value, LiteralKind type) {
        super(value, type);
    }

    public BooleanValue(Boolean value) {
        super(value, LiteralKind.BOOLEAN);
    }

    @Override
    public String getPrintable() {
        return "" + value;
    }
}
