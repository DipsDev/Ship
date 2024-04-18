package lexer;

import java.util.LinkedList;
import java.util.Queue;

public class Lexer {
    public Queue<Token> tokenize(String code) {
        Queue<Token> tokens = new LinkedList<>();
        for (int i = 0; i<code.length(); i++) {
            char current = code.charAt(i);

            if (current == '+' || current == '-' || current == '/' || current == '*') {
                tokens.add(new Token(Character.toString(current), TokenType.BINARY_OPERATOR));
            }
            else if (current == ' ' || current == '\n' || current == '\t' || current == ',') {
                continue;
            }
            else if (current == ';') {
                tokens.add(new Token(";", TokenType.EOL));
            }
            else if (Character.isDigit(current)) {
                StringBuilder builder = new StringBuilder();
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
                tokens.add(new Token(builder.toString(), TokenType.NUMBER));
            }
            else if (Character.isAlphabetic(current)) {
                StringBuilder builder = new StringBuilder();
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
                tokens.add(new Token(builder.toString(), TokenType.IDENTIFIER));
            }
            else {
                throw new Error("Unrecognized token at " + current);
            }

        }

        return tokens;
    }
}
