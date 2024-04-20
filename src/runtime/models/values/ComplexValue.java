package runtime.models.values;

import parser.LiteralKind;
import runtime.models.RandomAccessValue;
import runtime.models.RuntimeValue;

import java.util.List;

public class ComplexValue extends RuntimeValue<List<RuntimeValue<?>>> implements RandomAccessValue {
    public ComplexValue(List<RuntimeValue<?>> value, LiteralKind type) {
        super(value, type);
    }

    @Override
    public String getPrintable() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i<value.size(); i++) {
            RuntimeValue<?> obj = value.get(i);
            if (i == value.size() - 1) {
                builder.append(obj.getPrintable());
                continue;
            }
            builder.append(obj.getPrintable()).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public RuntimeValue<?> getAt(int num) {
        return value.get(num);
    }
}
