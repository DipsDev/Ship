import lexer.Lexer;
import lexer.Token;

import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        String code = """
                           String
                      """;
        Lexer lexer = new Lexer();

        Queue<Token> queue = lexer.tokenize(code);
        queue.forEach(System.out::println);
    }
}