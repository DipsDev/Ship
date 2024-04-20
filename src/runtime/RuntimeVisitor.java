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
    public abstract RuntimeValue visit(BooleanExpr booleanExpr);
    public abstract RuntimeValue visit(IfStmt ifStmt);




}
