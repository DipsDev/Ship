package runtime;

import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public class FunctionVisitor extends RuntimeVisitor {
    @Override
    public RuntimeValue visit(AssignStmt stmt) {
        Variable var = this.getVariable(stmt.getLhs());
        if (var == null) {
            throw new RuntimeException("NameError: name '" + stmt.getLhs() + "' is undefined");
        }
        if (var.isConstant()) {
            throw new RuntimeException("AssignmentError: cannot assign a value to const '" + stmt.getLhs() + "'");
        }
        var.setValue(stmt.getRhs().accept(this));
        return NIL;
    }

    @Override
    public RuntimeValue visit(BasicLit basicLit) {
        return new RuntimeValue(basicLit.getValue(), basicLit.getKind());
    }

    @Override
    public RuntimeValue visit(BinaryExpr binaryExpr) {
        RuntimeValue leftValue = binaryExpr.getLeft().accept(this);
        RuntimeValue rightValue = binaryExpr.getRight().accept(this);

        if (leftValue.getType() != LiteralKind.INT) {
            throw new RuntimeException("TypeError: Cannot do binary operations on non numbers. Got " + leftValue.getValue());
        }
        if (rightValue.getType() != LiteralKind.INT) {
            throw new RuntimeException("TypeError: Cannot do binary operations on non numbers. Got " + rightValue.getValue());
        }

        Number value;
        switch (binaryExpr.getOp().charAt(0)) {
            case '+' -> {
                value = Integer.parseInt(leftValue.getValue()) + Integer.parseInt(rightValue.getValue());
            }
            case '-' -> {
                value = Integer.parseInt(leftValue.getValue()) - Integer.parseInt(rightValue.getValue());
            }
            case '*' -> {
                value = Integer.parseInt(leftValue.getValue()) * Integer.parseInt(rightValue.getValue());
            }
            default -> {
                value = Integer.parseInt(leftValue.getValue()) / Integer.parseInt(rightValue.getValue());
            }
        }
        return new RuntimeValue(value.toString(), LiteralKind.INT);
    }

    @Override
    public RuntimeValue visit(CallExpr callExpr) {
        Function function = getFunction(callExpr.getName());
        if (function == null) {
            throw new RuntimeException("NameError: name '" + callExpr.getName() + "' is undefined");
        }

        return NIL;
    }

    @Override
    public RuntimeValue visit(ReturnStmt returnStmt) {
        return returnStmt.getResult().accept(this);
    }

    @Override
    public RuntimeValue visit(DeclStmt stmt) {
        if (this.getVariable(stmt.getName()) != null) {
            throw new RuntimeException("TypeError: name '" + stmt.getName() + "' is already defined in the current context");
        }
        createVariable(new Variable(stmt.getName(), stmt.getValue().accept(this), stmt.getTok().equals("const")));
        return NIL;
    }

    @Override
    public RuntimeValue visit(FuncDecl funcDecl) {
        return null;
    }

    @Override
    public RuntimeValue visit(Ident ident) {
        Variable var = getVariable(ident.getName());
        if (var == null) {
            throw new RuntimeException("TypeError: name '" + ident.getName() + "' is undefined");
        }
        return var.getValue();
    }

    public void applyScope(HashMap<String, Variable> variableHashMap, HashMap<String, Function> functionHashMap) {
        this.functions.putAll(functionHashMap);
        this.variables.putAll(variableHashMap);
    }
}
