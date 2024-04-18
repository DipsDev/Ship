package lexer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LexerQueue {
    ArrayList<Token> tokens;
    int current;

    public LexerQueue() {
        current = 0;
        this.tokens = new ArrayList<>();
    }

    public Token advance() {
        return tokens.get(current++);
    }

    public Token peek() {
        return tokens.get(current + 1);
    }

    public Token get() {
        return tokens.get(current);
    }

    public void add(Token tkn) {
        tokens.add(tkn);
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
