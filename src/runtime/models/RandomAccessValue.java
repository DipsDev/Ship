package runtime.models;

public interface RandomAccessValue {

    public RuntimeValue<?> getAt(int index);
}
