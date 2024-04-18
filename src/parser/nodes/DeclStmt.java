package parser.nodes;

import lexer.LexerQueue;
import lexer.Token;
import parser.Node;

public class DeclStmt extends Node {

    Node value;
    String type;
    String name;

    public DeclStmt(Node value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    public Node getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DeclStmt{" +
                "value=" + value +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
