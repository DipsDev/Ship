package runtime;

import errors.ShipSyntaxError;
import errors.ShipTypeError;
import parser.LiteralKind;
import parser.Node;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.Variable;
import runtime.models.values.ComplexValue;

import java.util.HashMap;

public class FunctionVisitor extends RuntimeVisitor {

    @Override
    public RuntimeValue<?> visit(IfStmt ifStmt) {
        RuntimeValue<?> bool = ifStmt.getExpr().accept(this);
        if (bool.getType() != LiteralKind.BOOLEAN) {
            throw new ShipTypeError("if statement can only receive boolean expressions", bool.getValue().toString(), ifStmt.getLocation());
        }
        boolean value = (Boolean) bool.getValue();
        if (!value) {
            return NIL;
        }
        for (Node nd : ifStmt.getBody()) {
            RuntimeValue<?> val = nd.accept(this);
            if (nd instanceof ReturnStmt) {
                return val;
            }
        }
        return NIL;

    }

    @Override
    public RuntimeValue<?> visit(ReturnStmt returnStmt) {
        return returnStmt.getResult().accept(this);
    }


    @Override
    public RuntimeValue<?> visit(FuncDecl funcDecl) {
        throw new ShipSyntaxError("inner functions are no supported", funcDecl.getName(), funcDecl.getLocation());
    }

    public void applyScope(HashMap<String, Variable> variableHashMap, HashMap<String, Function> functionHashMap) {
        this.functions.putAll(functionHashMap);
        this.variables.putAll(variableHashMap);
    }
}
