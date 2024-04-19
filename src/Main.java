import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipRuntime;
import runtime.ShipVisitor;

public class Main {
    public static void main(String[] args) {
        String code = """
                        let t = 0;
                        const four = 4;
                        fn hello_world(a, b, c) {
                            t = a + b + c
                        }
                        hello_world(8 / 2, 4 * 3, four);
                      """;
        Lexer lexer = new Lexer();

        LexerQueue queue = lexer.tokenize(code);
        // queue.getTokens().forEach(System.out::println);

        ShipParser parser = new ShipParser(queue);
        Program program = parser.build();

        program.getBody().forEach(System.out::println);

        ShipRuntime shipRuntime = new ShipRuntime();
        shipRuntime.execute(program);

        shipRuntime.getVariables().forEach((s, v) -> System.out.println(v));


    }
}