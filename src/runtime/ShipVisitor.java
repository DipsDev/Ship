package runtime;

import parser.Node;
import parser.nodes.*;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public class ShipVisitor extends RuntimeVisitor {

    @Override
    public RuntimeValue visit(AssignStmt stmt) {
        return null;
    }

    @Override
    public RuntimeValue visit(BasicLit basicLit) {
        return null;
    }

    @Override
    public RuntimeValue visit(BinaryExpr binaryExpr) {
        return null;
    }

    @Override
    public RuntimeValue visit(CallExpr callExpr) {
        return null;
    }

    @Override
    public RuntimeValue visit(DeclStmt stmt) {
        return null;
    }

    @Override
    public RuntimeValue visit(FuncDecl funcDecl) {
        return null;
    }

    @Override
    public RuntimeValue visit(FuncParam param) {
        return null;
    }

    @Override
    public RuntimeValue visit(Ident ident) {
        return null;
    }
}
