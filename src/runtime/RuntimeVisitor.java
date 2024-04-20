package runtime;

import errors.ShipNameError;
import errors.ShipTypeError;
import parser.BaseKind;
import parser.LiteralKind;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;
import runtime.models.values.BooleanValue;
import runtime.models.values.ComplexValue;
import runtime.models.values.NumberValue;

import java.util.HashMap;

public abstract class RuntimeVisitor {

    public static final RuntimeValue<?> NIL = new RuntimeValue<>(null, LiteralKind.NULL);

    protected HashMap<String, Variable> variables;
    protected HashMap<String, Function> functions;

    public RuntimeVisitor() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    protected void createFunction(Function function) {
        functions.put(function.getName(), function);
    }

    protected Function getFunction(String name) {
        return  functions.get(name);
    }

    protected void createVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }
    protected Variable getVariable(String name) {
        return variables.get(name);
    }

    public RuntimeValue<?> visit(AssignStmt stmt) {
        Variable var = this.getVariable(stmt.getLhs());
        if (var == null) {
            throw new ShipNameError("name is not defined", stmt.getLhs(), stmt.getLocation());
        }
        if (var.isConstant()) {
            throw new ShipTypeError("invalid assignment to const", stmt.getLhs(), stmt.getLocation());
        }
        var.setValue(stmt.getRhs().accept(this));
        return NIL;
    }
    public RuntimeValue<?> visit(BasicLit basicLit) {
        return new RuntimeValue<>(basicLit.getValue(), basicLit.getKind());
    }

    public NumberValue visit(BinaryExpr binaryExpr) {
        RuntimeValue<?> leftValue = binaryExpr.getLeft().accept(this);
        RuntimeValue<?> rightValue = binaryExpr.getRight().accept(this);

        if (leftValue.getType().getBase() != BaseKind.NUMBER) {
            throw new ShipTypeError("unsupported operand type(s)", leftValue.getValue().toString(), binaryExpr.getLeft().getLocation());
        }
        if (rightValue.getType().getBase() != BaseKind.NUMBER) {
            throw new ShipTypeError("unsupported operand type(s)", rightValue.getValue().toString(), binaryExpr.getRight().getLocation());
        }

        float lvalue = ((Number) leftValue.getValue()).floatValue();
        float rvalue = ((Number) rightValue.getValue()).floatValue();

        Number value;
        switch (binaryExpr.getOp().charAt(0)) {
            case '+' -> {
                value = lvalue + rvalue;
            }
            case '-' -> {
                value = lvalue - rvalue;
            }
            case '*' -> {
                value = lvalue * rvalue;
            }
            case '%' -> {
                value = lvalue % rvalue;
            }
            default -> {
                value = lvalue / rvalue;
            }
        }
        if (leftValue.getType() != LiteralKind.FLOAT && rightValue.getType() != LiteralKind.FLOAT) {
            return new NumberValue(value.intValue(), LiteralKind.INT);
        }
        return new NumberValue(value.doubleValue(), LiteralKind.FLOAT);
    }
    public RuntimeValue<?> visit(CallExpr callExpr) {
        Function function = getFunction(callExpr.getName());
        if (function == null) {
            throw new ShipNameError("name is not defined", callExpr.getName(), callExpr.getLocation());
        }
        FunctionVisitor visitor = new FunctionVisitor();
        visitor.applyScope(this.variables, this.functions);
        for (int i = 0; i < function.getArguments().size(); i++) {
            String name = function.getArguments().get(i);
            RuntimeValue<?> value = callExpr.getParams().get(i).accept(this);
            visitor.createVariable(new Variable(name, value, false));
        }
        return function.run(visitor);


    }
    public RuntimeValue<?> visit(DeclStmt stmt) {
        if (this.getVariable(stmt.getName()) != null) {
            throw new ShipNameError("name is already defined in the current context", stmt.getName() ,stmt.getLocation());
        }
        createVariable(new Variable(stmt.getName(), stmt.getValue().accept(this), stmt.getTok().equals("const")));
        return NIL;
    }
    public abstract RuntimeValue<?> visit(FuncDecl funcDecl);
    public RuntimeValue<?> visit(Ident ident) {
        Variable var = getVariable(ident.getName());
        if (var == null) {
            throw new ShipNameError("name is not defined", ident.getName() , ident.getLocation());
        }
        return var.getValue();
    }
    public abstract RuntimeValue<?> visit(ReturnStmt returnStmt);
    public NumberValue visit(UnaryExpr unaryExpr) {
        RuntimeValue<?> value = unaryExpr.getValue().accept(this);
        if (value.getType().getBase() != BaseKind.NUMBER) {
            throw new ShipTypeError("unsupported operand type(s)", value.getValue().toString(), unaryExpr.getLocation());
        }
        if (unaryExpr.getSign() == '+') {
            return new NumberValue((Number) value.getValue(), value.getType());
        }

        Number newValue = ((Number) value.getValue()).floatValue();
        if (value.getType() == LiteralKind.INT) {
            return new NumberValue(-1 * newValue.intValue(), LiteralKind.INT);
        }
        return new NumberValue(-1 * newValue.doubleValue(), LiteralKind.FLOAT);



    }
    public boolean calculateGL(Number left, Number right, String op) {
        if (op.equals(">=")) {
            return left.doubleValue() >= right.doubleValue();
        }
        if (op.equals(">")) {
            return left.doubleValue() > right.doubleValue();
        }
        if (op.equals("<")) {
            return left.doubleValue() < right.doubleValue();
        }

        return left.doubleValue() >= right.doubleValue();

    }

    public BooleanValue visit(BooleanExpr booleanExpr) {
        RuntimeValue<?> leftVal = booleanExpr.getLeft().accept(this);
        RuntimeValue<?> rightVal = booleanExpr.getRight().accept(this);

        if (booleanExpr.getOp().equals("==")) {
            if (leftVal.getValue().equals(rightVal.getValue())) {
                return new BooleanValue(true, LiteralKind.BOOLEAN);
            }
            return new BooleanValue(false, LiteralKind.BOOLEAN);
        }
        if (booleanExpr.getOp().equals("!=")) {
            if (leftVal.getValue().equals(rightVal.getValue())) {
                return new BooleanValue(false, LiteralKind.BOOLEAN);
            }
            return new BooleanValue(true, LiteralKind.BOOLEAN);
        }
        if (leftVal.getType().getBase() != rightVal.getType().getBase()) {
            throw new ShipTypeError(String.format("'%s' not supported between instances of '%s' and '%s'",
                    booleanExpr.getOp(), leftVal.getType().name().toLowerCase(), rightVal.getType().name().toLowerCase()),
                    "", booleanExpr.getLocation());
        }

        if (leftVal.getType().getBase() == BaseKind.NUMBER) {
            float lvalue = ((Number) leftVal.getValue()).floatValue();
            float rvalue = ((Number) rightVal.getValue()).floatValue();

            boolean value = calculateGL(lvalue, rvalue, booleanExpr.getOp());
            return new BooleanValue(value, LiteralKind.BOOLEAN);
        }
        if (leftVal.getType() == LiteralKind.STRING) {
            String lvalue = ((String) leftVal.getValue());
            String rvalue = ((String) rightVal.getValue());

            boolean value = calculateGL((int) lvalue.charAt(0), (int) rvalue.charAt(0), booleanExpr.getOp());
            return new BooleanValue(value, LiteralKind.BOOLEAN);
        }
        throw new ShipTypeError("not supported type for boolean expression", leftVal.getType().name(), booleanExpr.getLocation());


    }
    public abstract RuntimeValue<?> visit(IfStmt ifStmt);

    public abstract ComplexValue visit(ArrayLit arrayLit);




}
