package runtime;

import parser.nodes.*;
import runtime.models.RuntimeValue;
import runtime.models.Variable;

import java.util.HashMap;

public abstract class RuntimeVisitor {

    public static final RuntimeValue NIL = new RuntimeValue(null, LiteralKind.NULL);

    protected HashMap<String, Variable> variables;

    public RuntimeVisitor() {
        this.variables = new HashMap<>();
    }

    protected void createVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }
    protected Variable getVariable(String name) {
        return variables.get(name);
    }

    public abstract RuntimeValue visit(AssignStmt stmt);
    public abstract RuntimeValue visit(BasicLit basicLit);
    public abstract RuntimeValue visit(BinaryExpr binaryExpr);
    public abstract RuntimeValue visit(CallExpr callExpr);
    public abstract RuntimeValue visit(DeclStmt stmt);
    public abstract RuntimeValue visit(FuncDecl funcDecl);
    public abstract RuntimeValue visit(FuncParam param);
    public abstract RuntimeValue visit(Ident ident);




}
