package runtime.models;

public class Variable {
    String name;
    RuntimeValue value;
    boolean constant;

    public Variable(String name, RuntimeValue value, boolean constant) {
        this.name = name;
        this.constant = constant;
        this.value = value;
    }

    public RuntimeValue getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(RuntimeValue value) {
        this.value = value;
    }

    public boolean isConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", constant=" + constant +
                '}';
    }
}
