package lexer;

import errors.ShipSyntaxError;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lexer {
    private final HashMap<String, TokenType> reservedKeywords;

    public  Lexer() {
        this.reservedKeywords = new HashMap<>();

        // Variable related
        this.reservedKeywords.put("let", TokenType.LET);
        this.reservedKeywords.put("const", TokenType.CONST);

        // Function related
        this.reservedKeywords.put("fn", TokenType.FUNCTION);
        this.reservedKeywords.put("return", TokenType.RETURN);

        // Misc
        this.reservedKeywords.put("false", TokenType.BOOLEAN);
        this.reservedKeywords.put("true", TokenType.BOOLEAN);
        this.reservedKeywords.put("nil", TokenType.NULL);
        this.reservedKeywords.put("if", TokenType.IF);


    }

    public LexerQueue tokenize(String code) {
        LexerQueue tokens = new LexerQueue();
        StringBuilder builder = new StringBuilder();
        int col = 1;
        int line = 1;
        for (int i = 0; i<code.length(); i++) {
            char current = code.charAt(i);
            if (current == '+' || current == '-' || current == '/' || current == '*' || current == '%') {
                tokens.add(new Token(Character.toString(current), TokenType.BINARY_OPERATOR, line, col));
            }
            else if (current == ' ' || current == '\n' || current == '\t' || current == ',' || current == '\r') {
                if (current == '\n') {
                    line++;
                    col = 1;
                    continue;
                }
                else if (current == '\t') {
                    col += 4;
                    continue;
                }
                else if (current == ' ') {
                    col += 1;
                    continue;
                }
                if (current == '\r') {
                    continue;
                }
            }
            else if (current == '<' || current == '>') {
                if (code.charAt(i + 1) == '=') {
                    tokens.add(new Token(current + "=", TokenType.BOOLEAN_OPERATOR, line, col));
                    col += 1;
                    i++;
                    continue;
                }
                tokens.add(new Token(current + "", TokenType.BOOLEAN_OPERATOR, line, col));
            }
            else if (current == '=') {
                if (code.charAt(i+1) == '=') {
                    col += 1;
                    tokens.add(new Token("==", TokenType.BOOLEAN_OPERATOR, line, col ));
                    i++;
                    continue;
                }
                tokens.add(new Token(Character.toString(current), TokenType.EQUALS, line, col));
            }
            else if (current == '!') {
                if (code.charAt(i+1) == '=') {
                    col += 1;
                    i++;
                    tokens.add(new Token("!=", TokenType.BOOLEAN_OPERATOR, line, col));
                    continue;
                }
                // When ! (not) implemented it will be put here
            }
            else if (current == ';') {
                tokens.add(new Token(";", TokenType.EOL, line, col ));
            }
            else if (current == '(') {
                tokens.add(new Token("(", TokenType.OPEN_PARAN, line, col));
            }
            else if (current == ')') {
                tokens.add(new Token(")", TokenType.CLOSE_PARAN, line, col));
            }
            else if (current == '{') {
                tokens.add(new Token("{", TokenType.OPEN_BLOCK, line, col));
            }
            else if (current == '[') {
                tokens.add(new Token("[", TokenType.OPEN_BRACKET, line, col));
            }
            else if (current == ']') {
                tokens.add(new Token("]", TokenType.CLOSE_BRACKET, line, col));
            }
            else if (current == '}') {
                tokens.add(new Token("}", TokenType.CLOSE_BLOCK, line, col));
            }
            else if (current == '"') {
                i++;
                char pos = code.charAt(i);
                while (pos != '"') {
                    builder.append(pos);
                    i++;
                    if (i >= code.length()) {
                        break;
                    }
                    pos = code.charAt(i);
                }
                tokens.add(new Token(builder.toString(), TokenType.STRING, line, col));
                col += builder.length();
                builder.setLength(0);
                continue;
            }
            else if (Character.isDigit(current)) {
                char pos = current;
                boolean isFloat = false;
                while (Character.isDigit(pos) || pos == '.') {
                    builder.append(pos);
                    i++;
                    if (pos == '.') {
                        if (isFloat) {
                            throw new ShipSyntaxError("invalid syntax", ".", col + ":" + line);
                        }
                        isFloat = true;
                    }

                    if (i < code.length()) {
                        pos = code.charAt(i);
                    } else {
                        break;
                    }
                }
                i--;
                if (isFloat) {
                    tokens.add(new Token(builder.toString(), TokenType.FLOAT, line, col));
                } else {
                    tokens.add(new Token(builder.toString(), TokenType.INTEGER, line, col));
                }
                col += builder.length();
                builder.setLength(0);
                continue;
            }
            else if (Character.isAlphabetic(current)) {

                char pos = current;
                while (Character.isLetterOrDigit(pos) || pos == '_') {
                    builder.append(pos);
                    i++;
                    if (i < code.length()) {
                        pos = code.charAt(i);
                    } else {
                        break;
                    }
                }
                i--;

                if (reservedKeywords.containsKey(builder.toString())) {
                    tokens.add(new Token(builder.toString(), reservedKeywords.get(builder.toString()), line, col));
                    col += builder.length();
                    builder.setLength(0);
                    continue;
                }
                tokens.add(new Token(builder.toString(), TokenType.IDENTIFIER, line, col));
                col += builder.length();
                builder.setLength(0);
                continue;
            }
            else {
                throw new ShipSyntaxError("Unrecognized token", Character.toString(current), col + ":" + line);
            }
            col++;

        }
        tokens.add(new Token("", TokenType.EOF, line, col));
        return tokens;
    }
}
