package parser;

import lexer.LexerQueue;
import lexer.Token;
import lexer.TokenType;
import parser.nodes.*;

import java.text.MessageFormat;

public class ShipParser {
    private final LexerQueue tokens;

    public ShipParser(LexerQueue tokens) {
        this.tokens = tokens;
    }

    public DeclStmt parseDeclStmt() {
        Token tok = tokens.advance();
        String name = tokens.advance().getValue();

        // remove the equal sign
        tokens.advance();

        Node val = this.parse();

        if (val instanceof FuncDecl) {
            throw new RuntimeException("SyntaxError: Invalid syntax.");
        }

        return new DeclStmt(val, tok.getValue(), name);
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
            case BINARY_OPERATOR -> {
                if (current.getValue().charAt(0) != '-' && current.getValue().charAt(0) != '+') {
                    throw new RuntimeException("SyntaxError: unknown operator usage");
                }
                return new UnaryExpr(current.getValue().charAt(0), parsePrimaryExpression());
            }
            default -> {
                throw new RuntimeException("invalid operation: mismatched type " + current.getType());
            }


        }
    }

    private Node parseCallExpr() {
        String variableName = tokens.advance().getValue();
        tokens.advance(); // (
        CallExpr callExpr = new CallExpr(variableName);
        while (!tokens.get().getValue().equals(")")) {
            callExpr.addParam(this.parse());
        }
        tokens.advance();
        return callExpr;

    }

    private Node parseIdent() {
        // a + 5 * 4;
        if (tokens.peek().getType() == TokenType.BINARY_OPERATOR) {
            return parseBinaryExpr();
        }
        // a();
        if (tokens.peek().getType() == TokenType.OPEN_PARAN) {
            return parseCallExpr();
        }
        if (tokens.peek().getType() == TokenType.EQUALS) {
            String variableName = tokens.advance().getValue();
            // remove the equals sign
            tokens.advance();
            Node val = this.parse();
            return new AssignStmt(variableName, val);
        }
        return new Ident(tokens.advance().getValue());


    }

    private Node parseReturnStmt() {
        this.tokens.advance();
        Node result = this.parse();
        return new ReturnStmt(result);
    }

    private Node parseFuncDecl() {
        // Remove fn keyword
        this.tokens.advance();
        String fnName = this.tokens.advance().getValue();
        FuncDecl funcDecl = new FuncDecl(fnName);
        if (this.tokens.advance().getType() != TokenType.OPEN_PARAN) {
            throw new RuntimeException("Unexpected Token, expected (");
        }
        while (this.tokens.get().getType() != TokenType.CLOSE_PARAN) {
            String paramName = this.tokens.advance().getValue();
            funcDecl.appendParam(paramName);
        }
        // Remove the last CLOSE_PARAN
        tokens.advance();

        // Add the function block
        if (this.tokens.advance().getType() != TokenType.OPEN_BLOCK) {
            throw new RuntimeException("Unexpected Token, expected {");
        }

        while (this.tokens.get().getType() != TokenType.CLOSE_BLOCK) {
            if (tokens.get().getType() == TokenType.EOL) {
                this.tokens.advance();
                continue;
            }
            funcDecl.appendBody(this.parse());
        }
        tokens.advance();

        return funcDecl;

    }

    private Node parseUnaryExpr() {
        Token token = tokens.advance(); // +/- sign
        if (token.getValue().charAt(0) != '-' && token.getValue().charAt(0) != '+') {
            throw new RuntimeException("SyntaxError: Invalid syntax at " + token.getValue());
        }
        Node nd = parseBinaryExpr();
        return new UnaryExpr(token.getValue().charAt(0), nd);

    }



    private Node parse() {
        Token token = tokens.get();

        switch(token.getType()) {
            case NUMBER -> {
                return parseBinaryExpr();
            }
            case BINARY_OPERATOR -> {
                return parseUnaryExpr();

            }
            case STRING -> {
                return new BasicLit(tokens.advance().getValue(), LiteralKind.STRING);
            }
            case LET, CONST -> {
                return parseDeclStmt();
            }
            case IDENTIFIER -> {
                return parseIdent();
            }
            case FUNCTION -> {
                return parseFuncDecl();
            }
            case RETURN -> {
                return parseReturnStmt();
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
