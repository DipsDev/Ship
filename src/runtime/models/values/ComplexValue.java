package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RuntimeValue;

import java.util.List;

public class ComplexValue extends RuntimeValue<List<?>> {
    public ComplexValue(List<?> value, LiteralKind type) {
        super(value, type);
    }
}
