import errors.ShipError;
import lexer.Lexer;
import lexer.LexerQueue;
import parser.ShipParser;
import parser.nodes.Program;
import runtime.ShipRuntime;
import runtime.ShipVisitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        executeAtOnce(args[0]);


    }

    private static void executeAtOnce(String path) {
        try {
            String code = Files.readString(Path.of(path));
            Lexer lexer = new Lexer();
            LexerQueue lexerQueue = lexer.tokenize(code);
            ShipParser parser = new ShipParser(lexerQueue);
            Program pr = parser.build();
            pr.getBody().forEach(System.out::println);
            new ShipRuntime().execute(pr);


        } catch (IOException e) {
            throw new RuntimeException("Couldn't open file " + path);


        } catch (ShipError error) {
            error.printStackTrace();
        }
    }
}