package parser;

import parser.nodes.BasicLit;
import parser.nodes.LiteralKind;

// Ship static typechecker
public class ShipTC {

    public static LiteralKind tc(Node nd) {
        if (!(nd instanceof TypedNode typedNode)) {
            throw new RuntimeException("Unknown type for expression " + nd);
        }

        return typedNode.getType();





    }

}
