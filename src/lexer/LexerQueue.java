package lexer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LexerQueue {
    LinkedList<Token> tokens;

    public LexerQueue() {
        this.tokens = new LinkedList<>();
    }

    public Token advance() {
        return tokens.pollFirst();
    }

    public Token peek() {
        return tokens.get(1);
    }

    public Token get() {
        return tokens.peek();
    }

    public void add(Token tkn) {
        tokens.add(tkn);
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }
}
