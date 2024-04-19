import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipRuntime;
import runtime.ShipVisitor;

public class Main {
    public static void main(String[] args) {
        String code = """
                        const a = 5 + 5;
                        let b = 2 * a - 5;
                        const c = b - a + 20 / a;
                      """;
        Lexer lexer = new Lexer();

        LexerQueue queue = lexer.tokenize(code);
        // queue.getTokens().forEach(System.out::println);

        ShipParser parser = new ShipParser(queue);
        Program program = parser.build();

        ShipRuntime shipRuntime = new ShipRuntime();
        shipRuntime.execute(program);

        shipRuntime.getVariables().forEach((s, v) -> System.out.println(v));


    }
}