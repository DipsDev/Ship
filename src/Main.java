import lexer.Lexer;
import lexer.LexerQueue;
import lexer.Token;
import parser.ShipParser;
import parser.nodes.Program;

import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        String code = """
                        hello_world(hello(5));
                      """;
        Lexer lexer = new Lexer();

        LexerQueue queue = lexer.tokenize(code);
        // queue.getTokens().forEach(System.out::println);

        ShipParser parser = new ShipParser(queue);
        Program program = parser.build();
        program.getBody().forEach(System.out::println);
    }
}