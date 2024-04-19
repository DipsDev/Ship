package parser;

import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public abstract class Node {
    public RuntimeValue accept(RuntimeVisitor runtime) {
        return RuntimeVisitor.NIL;
    }

}
