package runtime;

import errors.ShipTypeError;
import parser.BaseKind;
import parser.LiteralKind;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public abstract class RuntimeVisitor {

    public static final RuntimeValue NIL = new RuntimeValue(null, LiteralKind.NULL);

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

    public abstract RuntimeValue visit(AssignStmt stmt);
    public abstract RuntimeValue visit(BasicLit basicLit);
    public RuntimeValue visit(BinaryExpr binaryExpr) {
        RuntimeValue leftValue = binaryExpr.getLeft().accept(this);
        RuntimeValue rightValue = binaryExpr.getRight().accept(this);

        if (leftValue.getType().getBase() != BaseKind.NUMBER) {
            throw new ShipTypeError("unsupported operand type(s)", leftValue.getValue(), binaryExpr.getLeft().getLocation());
        }
        if (rightValue.getType().getBase() != BaseKind.NUMBER) {
            throw new ShipTypeError("unsupported operand type(s)", rightValue.getValue(), binaryExpr.getRight().getLocation());
        }

        Number value;
        switch (binaryExpr.getOp().charAt(0)) {
            case '+' -> {
                value = Double.parseDouble(leftValue.getValue()) + Double.parseDouble(rightValue.getValue());
            }
            case '-' -> {
                value = Double.parseDouble(leftValue.getValue()) - Double.parseDouble(rightValue.getValue());
            }
            case '*' -> {
                value = Double.parseDouble(leftValue.getValue()) * Double.parseDouble(rightValue.getValue());
            }
            case '%' -> {
                value = Double.parseDouble(leftValue.getValue()) % Double.parseDouble(rightValue.getValue());
            }
            default -> {
                value = Double.parseDouble(leftValue.getValue()) / Double.parseDouble(rightValue.getValue());
            }
        }
        if (leftValue.getType() != LiteralKind.FLOAT && rightValue.getType() != LiteralKind.FLOAT) {
            return new RuntimeValue(value.intValue() + "", LiteralKind.INT);
        }
        return new RuntimeValue(value.doubleValue() + "", LiteralKind.FLOAT);
    }
    public abstract RuntimeValue visit(CallExpr callExpr);
    public abstract RuntimeValue visit(DeclStmt stmt);
    public abstract RuntimeValue visit(FuncDecl funcDecl);
    public abstract RuntimeValue visit(Ident ident);
    public abstract RuntimeValue visit(ReturnStmt returnStmt);
    public abstract RuntimeValue visit(UnaryExpr unaryExpr);
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

    public RuntimeValue visit(BooleanExpr booleanExpr) {
        RuntimeValue leftVal = booleanExpr.getLeft().accept(this);
        RuntimeValue rightVal = booleanExpr.getRight().accept(this);

        if (booleanExpr.getOp().equals("==")) {
            if (leftVal.getValue().equals(rightVal.getValue())) {
                return new RuntimeValue("true", LiteralKind.BOOLEAN);
            }
            return new RuntimeValue("false", LiteralKind.BOOLEAN);
        }
        if (booleanExpr.getOp().equals("!=")) {
            if (leftVal.getValue().equals(rightVal.getValue())) {
                return new RuntimeValue("false", LiteralKind.BOOLEAN);
            }
            return new RuntimeValue("true", LiteralKind.BOOLEAN);
        }
        if (leftVal.getType().getBase() != rightVal.getType().getBase()) {
            throw new ShipTypeError(String.format("'%s' not supported between instances of '%s' and '%s'",
                    booleanExpr.getOp(), leftVal.getType().name().toLowerCase(), rightVal.getType().name().toLowerCase()),
                    "", booleanExpr.getLocation());
        }

        if (leftVal.getType().getBase() == BaseKind.NUMBER) {
            boolean value = calculateGL(Double.parseDouble(leftVal.getValue()), Double.parseDouble(rightVal.getValue()), booleanExpr.getOp());
            return new RuntimeValue(value + "", LiteralKind.BOOLEAN);
        }
        boolean value = calculateGL((int) leftVal.getValue().charAt(0), (int) rightVal.getValue().charAt(0), booleanExpr.getOp());
        return new RuntimeValue(value + "", LiteralKind.BOOLEAN);

    }
    public abstract RuntimeValue visit(IfStmt ifStmt);




}
