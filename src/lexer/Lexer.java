package lexer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lexer {
    private HashMap<String, TokenType> reservedKeywords;

    public  Lexer() {
        this.reservedKeywords = new HashMap<>();

        this.reservedKeywords.put("int", TokenType.LiteralType);
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
                tokens.add(new Token(Character.toString(current), TokenType.EQUALS));
            }
            else if (current == ';') {
                tokens.add(new Token(";", TokenType.EOL));
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
                while (Character.isAlphabetic(pos)) {
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
