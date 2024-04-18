package lexer;

public class Token {
    private String value;
    private TokenType type;

    public Token(String value, TokenType type) {
        this.type = type;
        this.value = value;
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
