package parser.nodes;

import lexer.LexerQueue;
import lexer.Token;
import parser.Node;

public class DeclStmt extends Node {

    Node value;
    String tok;
    String name;

    public DeclStmt(Node value, String tok, String name) {
        this.value = value;
        this.tok = tok;
        this.name = name;
    }

    public Node getValue() {
        return value;
    }

    public String getTok() {
        return tok;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DeclStmt{" +
                "value=" + value +
                ", tok='" + tok + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
