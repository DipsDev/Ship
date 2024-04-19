package parser;

import parser.nodes.LiteralKind;

public interface TypedNode {
    public LiteralKind getType();
}
