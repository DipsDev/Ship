import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipVisitor;

public class Main {
    public static void main(String[] args) {
        String code = """
                        const a = 5 + 5;
                        let b = 2 * a - 5;
                      """;
        Lexer lexer = new Lexer();

        LexerQueue queue = lexer.tokenize(code);
        // queue.getTokens().forEach(System.out::println);

        ShipParser parser = new ShipParser(queue);
        Program program = parser.build();

        ShipVisitor shipRuntime = new ShipVisitor();
        shipRuntime.execute(program);

        shipRuntime.getVariables().forEach((String, Variable) -> System.out.println(Variable));


    }
}