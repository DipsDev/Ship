import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipRuntime;
import runtime.ShipVisitor;

public class Main {
    public static void main(String[] args) {
        String code = """
                        fn factorial(a) {
                            if a == 0 {
                                return 1;
                            }
                            return a * factorial(a-1);
                        }
                        let a = factorial(10);
                        puts(a);
                      """;
        Lexer lexer = new Lexer();

        LexerQueue queue = lexer.tokenize(code);
        // queue.getTokens().forEach(System.out::println);

        ShipParser parser = new ShipParser(queue);
        Program program = parser.build();

        // program.getBody().forEach(System.out::println);

        ShipRuntime shipRuntime = new ShipRuntime();
        shipRuntime.execute(program);

        // shipRuntime.getVariables().forEach((s, v) -> System.out.println(v));


    }
}