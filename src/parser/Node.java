package parser;

import runtime.RuntimeVisitor;
import runtime.ShipVisitor;
import runtime.models.RuntimeValue;

public abstract class Node {
    String loc;

    public Node(String loc) {
        this.loc = loc;
    }

    public String getLocation() {
        return loc;
    }

    public RuntimeValue accept(RuntimeVisitor runtime) {
        return RuntimeVisitor.NIL;
    }

}
