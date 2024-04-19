package lexer;

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
        for (int i = 0; i<code.length(); i++) {
            char current = code.charAt(i);
            if (current == '+' || current == '-' || current == '/' || current == '*') {
                tokens.add(new Token(Character.toString(current), TokenType.BINARY_OPERATOR));
            }
            else if (current == ' ' || current == '\n' || current == '\t' || current == ',') {
                continue;
            }
            else if (current == '=') {
                if (code.charAt(i+1) == '=') {
                    tokens.add(new Token("==", TokenType.BOOLEAN_OPERATOR));
                    i++;
                    continue;
                }
                tokens.add(new Token(Character.toString(current), TokenType.EQUALS));
            }
            else if (current == '!') {
                if (code.charAt(i+1) == '=') {
                    i++;
                    tokens.add(new Token("!=", TokenType.BOOLEAN_OPERATOR));
                    continue;
                }
                // When ! (not) implemented it will be put here
            }
            else if (current == ';') {
                tokens.add(new Token(";", TokenType.EOL));
            }
            else if (current == '(') {
                tokens.add(new Token("(", TokenType.OPEN_PARAN));
            }
            else if (current == ')') {
                tokens.add(new Token(")", TokenType.CLOSE_PARAN));
            }
            else if (current == '{') {
                tokens.add(new Token("(", TokenType.OPEN_BLOCK));
            }
            else if (current == '}') {
                tokens.add(new Token("(", TokenType.CLOSE_BLOCK));
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
                tokens.add(new Token(builder.toString(), TokenType.STRING));
                builder.setLength(0);
            }
            else if (Character.isDigit(current)) {
                char pos = current;
                while (Character.isDigit(pos)) {
                    builder.append(pos);
                    i++;
                    if (i < code.length()) {
                        pos = code.charAt(i);
                    } else {
                        break;
                    }
                }
                i--;
                tokens.add(new Token(builder.toString(), TokenType.NUMBER));
                builder.setLength(0);
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
                    tokens.add(new Token(builder.toString(), reservedKeywords.get(builder.toString())));
                    builder.setLength(0);
                    continue;
                }
                tokens.add(new Token(builder.toString(), TokenType.IDENTIFIER));
                builder.setLength(0);
            }
            else {
                throw new Error("Unrecognized token at " + current);
            }

        }
        tokens.add(new Token("", TokenType.EOF));
        return tokens;
    }
}
