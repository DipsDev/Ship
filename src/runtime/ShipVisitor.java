package runtime;

import errors.ShipSyntaxError;
import errors.ShipTypeError;
import parser.LiteralKind;
import parser.Node;
import parser.nodes.*;
import runtime.models.Function;
import runtime.models.RuntimeValue;
import runtime.models.values.ComplexValue;

public class ShipVisitor extends RuntimeVisitor {



    @Override
    public RuntimeValue<?> visit(FuncDecl funcDecl) {
        createFunction(new Function(funcDecl.getName(), funcDecl.getBody(), funcDecl.getParams()));
        return NIL;
    }


    @Override
    public RuntimeValue<?> visit(ReturnStmt returnStmt) {
        throw new ShipSyntaxError("'return' outside function", returnStmt.getResult().toString() , returnStmt.getLocation());
    }



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
            nd.accept(this);
        }
        return NIL;

    }
}
