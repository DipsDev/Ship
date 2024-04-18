package parser;

import lexer.LexerQueue;
import lexer.Token;
import lexer.TokenType;
import parser.nodes.*;

public class ShipParser {
    private final LexerQueue tokens;

    public ShipParser(LexerQueue tokens) {
        this.tokens = tokens;
    }

    public DeclStmt parseDeclStmt() {
        Token type = tokens.advance();
        String name = tokens.advance().getValue();

        // remove the equal sign
        tokens.advance();

        Node val = this.parse();

        return new DeclStmt(val, type.getValue(), name);
    }

    public Node parseBinaryExpr() {
        Node left = parseMultiExpression();
        while (tokens.get().getValue().equals("+") || tokens.get().getValue().equals("-")) {
            String op = tokens.advance().getValue();
            Node right = parseMultiExpression();
            left = new BinaryExpr(op, left, right);
        }
        return left;

    }
    private Node parseMultiExpression() {
        Node left = parsePrimaryExpression();

        while (tokens.get().getValue().equals("*") || tokens.get().getValue().equals("/")) {
            String operator = tokens.advance().getValue();
            Node right = parsePrimaryExpression();
            left = new BinaryExpr(operator, left, right);
        }
        return left;
    }

    private Node parsePrimaryExpression() {
        Token current = tokens.advance();
        switch (current.getType()) {
            case NUMBER -> {
                return new BasicLit(current.getValue(), LiteralKind.INT);
            }
            case IDENTIFIER -> {
                return new Ident(current.getValue());
            }
            default -> {
                throw new RuntimeException("Couldn't parse correctly, got type " + current.getType());
            }


        }
    }

    private Node parseIdentifier() {
        if (tokens.peek().getType() == TokenType.BINARY_OPERATOR) {
            return parseBinaryExpr();
        }
        if (tokens.peek().getType() == TokenType.EOL) {
            return new Ident(tokens.advance().getValue());
        }

        // Variable assignment
        String variableName = tokens.advance().getValue();
        // remove the equals sign
        tokens.advance();
        Node val = this.parse();
        return new AssignStmt(variableName, val);

    }



    private Node parse() {
        Token token = tokens.get();

        switch(token.getType()) {
            case NUMBER -> {
                return parseBinaryExpr();
            }
            case LiteralType -> {
                return parseDeclStmt();
            }
            case IDENTIFIER -> {
                return parseIdentifier();
            }
        }
        throw new RuntimeException("Unexpected token " + token.getType());

    }

    public Program build() {
        Program program = new Program();

        while (this.tokens.get().getType() != TokenType.EOF) {
            if (tokens.get().getType() == TokenType.EOL) {
                this.tokens.advance();
                continue;
            }
            program.add(this.parse());
        }
        return program;
    }



}
