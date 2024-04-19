package runtime;

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
    public abstract RuntimeValue visit(BinaryExpr binaryExpr);
    public abstract RuntimeValue visit(CallExpr callExpr);
    public abstract RuntimeValue visit(DeclStmt stmt);
    public abstract RuntimeValue visit(FuncDecl funcDecl);
    public abstract RuntimeValue visit(Ident ident);




}
