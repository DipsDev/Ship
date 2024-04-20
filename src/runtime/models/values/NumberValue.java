package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RuntimeValue;

public class NumberValue extends RuntimeValue<Number> {
    public NumberValue(Number value, LiteralKind type) {
        super(value, type);
    }
}
