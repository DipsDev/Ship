package runtime;

import parser.Node;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public class ShipVisitor extends RuntimeVisitor {



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
        FunctionVisitor visitor = new FunctionVisitor();
        visitor.applyScope(this.variables, this.functions);
        for (int i = 0; i < function.getArguments().size(); i++) {
            String name = function.getArguments().get(i);
            RuntimeValue value = callExpr.getParams().get(i).accept(this);
            visitor.createVariable(new Variable(name, value, false));
        }
        for (Node node : function.getBody()) {
            RuntimeValue val = node.accept(visitor);
            if (val != NIL) {
                return val;
            }
        }
        return NIL;


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
        createFunction(new Function(funcDecl.getName(), funcDecl.getBody(), funcDecl.getParams()));
        return NIL;
    }

    @Override
    public RuntimeValue visit(Ident ident) {
        Variable var = getVariable(ident.getName());
        if (var == null) {
            throw new RuntimeException("TypeError: name '" + ident.getName() + "' is undefined");
        }
        return var.getValue();
    }

    @Override
    public RuntimeValue visit(ReturnStmt returnStmt) {
        throw new RuntimeException("SyntaxError: 'return' outside function");
    }
}
