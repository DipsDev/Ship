package runtime;

import errors.ShipNameError;
import errors.ShipSyntaxError;
import errors.ShipTypeError;
import parser.LiteralKind;
import parser.Node;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public class FunctionVisitor extends RuntimeVisitor {

    @Override
    public RuntimeValue visit(IfStmt ifStmt) {
        RuntimeValue bool = ifStmt.getExpr().accept(this);
        if (bool.getValue().equals("false")) {
            return NIL;
        }
        for (Node nd : ifStmt.getBody()) {
            RuntimeValue val = nd.accept(this);
            if (nd instanceof ReturnStmt) {
                return val;
            }
        }
        return NIL;

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
        return new RuntimeValue("false", LiteralKind.BOOLEAN);

    }


    @Override
    public RuntimeValue visit(UnaryExpr unaryExpr) {
        if (unaryExpr.getSign() == '+') {
            return unaryExpr.accept(this);
        }
        RuntimeValue value = unaryExpr.getValue().accept(this);
        if (value.getType() != LiteralKind.INT) {
            throw new ShipTypeError("unsupported operand type(s)", value.getValue(), unaryExpr.getLocation());
        }

        return new RuntimeValue(-1 * Integer.parseInt(value.getValue()) + "", LiteralKind.INT);
    }
    @Override
    public RuntimeValue visit(AssignStmt stmt) {
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

    @Override
    public RuntimeValue visit(BasicLit basicLit) {
        return new RuntimeValue(basicLit.getValue(), basicLit.getKind());
    }

    @Override
    public RuntimeValue visit(CallExpr callExpr) {
        Function function = getFunction(callExpr.getName());
        if (function == null) {
            throw new ShipNameError("name is not defined", callExpr.getName(), callExpr.getLocation());
        }
        FunctionVisitor visitor = new FunctionVisitor();
        visitor.applyScope(this.variables, this.functions);
        for (int i = 0; i < function.getArguments().size(); i++) {
            String name = function.getArguments().get(i);
            RuntimeValue value = callExpr.getParams().get(i).accept(this);
            visitor.createVariable(new Variable(name, value, false));
        }
        return function.run(visitor);


    }

    @Override
    public RuntimeValue visit(ReturnStmt returnStmt) {
        return returnStmt.getResult().accept(this);
    }

    @Override
    public RuntimeValue visit(DeclStmt stmt) {
        if (this.getVariable(stmt.getName()) != null) {
            throw new ShipNameError("name is already defined in the current context", stmt.getName(), stmt.getLocation());
        }
        createVariable(new Variable(stmt.getName(), stmt.getValue().accept(this), stmt.getTok().equals("const")));
        return NIL;
    }

    @Override
    public RuntimeValue visit(FuncDecl funcDecl) {
        throw new ShipSyntaxError("inner functions are no supported", funcDecl.getName(), funcDecl.getLocation());
    }

    @Override
    public RuntimeValue visit(Ident ident) {
        Variable var = getVariable(ident.getName());
        if (var == null) {
            throw new ShipNameError("name is not defined", ident.getName(), ident.getLocation());
        }
        return var.getValue();
    }

    public void applyScope(HashMap<String, Variable> variableHashMap, HashMap<String, Function> functionHashMap) {
        this.functions.putAll(functionHashMap);
        this.variables.putAll(variableHashMap);
    }
}
