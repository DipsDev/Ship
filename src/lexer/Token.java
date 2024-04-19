package lexer;

public class Token {
    private String value;
    private TokenType type;

    private int line;
    private int col;

    public Token(String value, TokenType type, int line, int col) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.col = col;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.type + " :: " + this. value;
    }
}
