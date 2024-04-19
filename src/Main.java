import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipRuntime;
import runtime.ShipVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String code;
        try {
            code = Files.readString(Path.of(args[0]));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open file " + args[0]);
        }
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