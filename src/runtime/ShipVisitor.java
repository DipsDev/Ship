package runtime;

import errors.ShipNameError;
import errors.ShipSyntaxError;
import errors.ShipTypeError;
import parser.BaseKind;
import parser.LiteralKind;
import parser.Node;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

public class ShipVisitor extends RuntimeVisitor {



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
    public RuntimeValue visit(DeclStmt stmt) {
        if (this.getVariable(stmt.getName()) != null) {
            throw new ShipNameError("name is already defined in the current context", stmt.getName() ,stmt.getLocation());
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
            throw new ShipNameError("name is not defined", ident.getName() , ident.getLocation());
        }
        return var.getValue();
    }

    @Override
    public RuntimeValue visit(ReturnStmt returnStmt) {
        throw new ShipSyntaxError("SyntaxError: 'return' outside function", returnStmt.getResult().toString() , returnStmt.getLocation());
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
    public RuntimeValue visit(IfStmt ifStmt) {
        RuntimeValue bool = ifStmt.getExpr().accept(this);
        if (bool.getValue().equals("false")) {
            return NIL;
        }
        for (Node nd : ifStmt.getBody()) {
            nd.accept(this);
        }
        return NIL;

    }
}
