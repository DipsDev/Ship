package parser;

import errors.ShipSyntaxError;
import errors.ShipTypeError;
import lexer.LexerQueue;
import lexer.Token;
import lexer.TokenType;
import parser.nodes.*;

import java.util.ArrayList;

public class ShipParser {
    private final LexerQueue tokens;

    public ShipParser(LexerQueue tokens) {
        this.tokens = tokens;
    }

    public DeclStmt parseDeclStmt() {
        Token tok = tokens.advance();
        Token name = tokens.advance();

        // remove the equal sign
        tokens.advance();

        Node val = this.parse();

        if (val instanceof FuncDecl) {
            throw new ShipSyntaxError("invalid syntax", ((FuncDecl) val).getName(), val.getLocation());
        }

        return new DeclStmt(val, tok.getValue(), name.getValue(), name.getLocation());
    }

    public Node parseBinaryExpr() {
        Node left = parseMultiExpression();
        while (tokens.get().getValue().equals("+") || tokens.get().getValue().equals("-")) {
            String op = tokens.advance().getValue();
            Node right = parseMultiExpression();
            left = new BinaryExpr(op, left, right, left.getLocation());
        }
        return left;

    }
    private Node parseMultiExpression() {
        Node left = parsePrimaryExpression();

        while (tokens.get().getValue().equals("*") || tokens.get().getValue().equals("/") || tokens.get().getValue().equals("%")) {
            String operator = tokens.advance().getValue();
            Node right = parsePrimaryExpression();
            left = new BinaryExpr(operator, left, right, left.getLocation());
        }
        return left;
    }

    private Node parsePrimaryExpression() {
        Token current = tokens.get();
        switch (current.getType()) {
            case INTEGER -> {
                return new BasicLit(tokens.advance().getValue(), LiteralKind.INT, current.getLocation());
            }
            case FLOAT -> {
                return new BasicLit(tokens.advance().getValue(), LiteralKind.FLOAT, current.getLocation());
            }
            case IDENTIFIER -> {
                if (tokens.peek().getType() == TokenType.OPEN_PARAN) {
                    return parseCallExpr();
                }
                return new Ident(tokens.advance().getValue(), current.getLocation());
            }
            case BINARY_OPERATOR -> {
                if (current.getValue().charAt(0) != '-' && current.getValue().charAt(0) != '+') {
                    throw new ShipSyntaxError("unknown operator usage", current.getValue(), current.getLocation());
                }
                return this.parseUnaryExpr();
            }
            case STRING -> {
                return new BasicLit(tokens.advance().getValue(), LiteralKind.STRING, current.getLocation());
            }
            default -> {
                throw new ShipTypeError("can only concatenate number", current.getValue(), current.getLocation());
            }


        }
    }

    private Node parseCallExpr() {
        Token variableName = tokens.advance(); // Get function name
        tokens.advance(); // Consume '('
        CallExpr callExpr = new CallExpr(variableName.getValue(), variableName.getLocation());
        while (!tokens.get().getValue().equals(")")) {
            callExpr.addParam(this.parse());
        }
        tokens.advance(); // consume )
        return callExpr;

    }

    private Node parseBooleanExpr() {
        Token current = this.tokens.advance();
        Node left = new Ident(current.getValue(), current.getLocation());
        Token op = this.tokens.advance();
        Node right = this.parse();
        return new BooleanExpr(op.getValue(), left, right, current.getLocation());
    }

    private Node parseIdent() {
        // a + 5 * 4;
        if (tokens.peek().getType() == TokenType.BINARY_OPERATOR) {
            return parseNumberExpr();
        }
        if (tokens.peek().getType() == TokenType.OPEN_PARAN) {
            Node nd = parseNumberExpr();
            return nd;
        }
        if (tokens.peek().getType() == TokenType.BOOLEAN_OPERATOR) {
            return parseBooleanExpr();
        }
        if (tokens.peek().getType() == TokenType.EQUALS) {
            Token variable = tokens.advance();
            // remove the equals sign
            tokens.advance();
            Node val = this.parse();
            return new AssignStmt(variable.getValue(), val, variable.getLocation());
        }
        Token tkn = tokens.advance();
        return new Ident(tkn.getValue(), tkn.getLocation());


    }

    private Node parseReturnStmt() {
        this.tokens.advance();
        Node result = this.parse();
        return new ReturnStmt(result, result.getLocation());
    }

    private Node parseFuncDecl() {
        // Remove fn keyword
        this.tokens.advance();
        Token fn = this.tokens.advance();
        FuncDecl funcDecl = new FuncDecl(fn.getValue(), fn.getLocation());
        if (this.tokens.get().getType() != TokenType.OPEN_PARAN) {
            throw new ShipSyntaxError("unexpected Token, expected '('", this.tokens.get().getValue(), this.tokens.get().getLocation());
        }
        this.tokens.advance();

        while (this.tokens.get().getType() != TokenType.CLOSE_PARAN) {
            Token param = this.tokens.advance();
            if (param.getType() != TokenType.IDENTIFIER) {
                throw new ShipSyntaxError("invalid syntax", "'" + param.getValue(), param.getLocation());
            }
            funcDecl.appendParam(param.getValue());
        }
        // Remove the last CLOSE_PARAN
        tokens.advance();

        // Add the function block
        if (this.tokens.get().getType() != TokenType.OPEN_BLOCK) {
            throw new ShipSyntaxError("unexpected Token, expected {", this.tokens.get().getValue(), this.tokens.get().getLocation());
        }
        this.tokens.advance();

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
            throw new ShipSyntaxError("invalid syntax", token.getValue() , token.getLocation());
        }
        Token tkn = tokens.get();
        if (tkn.getType() == TokenType.STRING) {
            throw new ShipTypeError("cannot use boolean operators on string type", tkn.getValue(), tkn.getLocation());
        }
        Node nd = this.parsePrimaryExpression();
        return new UnaryExpr(token.getValue().charAt(0), nd, token.getLocation());

    }


    private Node parseNumberExpr() {
        Node left = parseBinaryExpr();
        if (tokens.get().getType() != TokenType.BOOLEAN_OPERATOR) {
            return left;
        }
        String op = tokens.advance().getValue();
        Node right = parseBinaryExpr();
        return new BooleanExpr(op, left, right, left.getLocation());
    }

    private Node parseString() {
        Token string = tokens.advance();
        switch (tokens.get().getType()) {
            case BOOLEAN_OPERATOR -> {
                Token op = tokens.advance();
                return new BooleanExpr(op.getValue(), new BasicLit(string.getValue(), LiteralKind.STRING, string.getLocation()), this.parse(), string.getLocation());
            }
            case BINARY_OPERATOR -> {
                throw new ShipTypeError("unsupported operand type(s)", tokens.get().getValue(), tokens.get().getLocation());
            }
            default -> {
                return new BasicLit(string.getValue(), LiteralKind.STRING, string.getLocation());
            }
        }


    }

    private Node parseArrDecl() {
        ArrayLit arr = new ArrayLit(this.tokens.advance().getLocation()); // eat the '['
        while (tokens.get().getType() != TokenType.CLOSE_BRACKET) {
            arr.addElement(this.parse());
        }
        tokens.advance(); // eat the ']'
        return arr;
    }


    private Node parseIfStmt() {
        Token ifWord = tokens.advance(); // remove the if keyword
        Node expr = this.parse();
        ArrayList<Node> body = new ArrayList<>();

        if (tokens.get().getType() != TokenType.OPEN_BLOCK) {
            throw new ShipSyntaxError("Expected {", tokens.get().getValue(), tokens.get().getLocation());
        }
        tokens.advance(); // remove the first {

        while (tokens.get().getType() != TokenType.CLOSE_BLOCK) {
            if (tokens.get().getType() == TokenType.EOL) {
                this.tokens.advance();
                continue;
            }
            body.add(this.parse());
        }
        tokens.advance();

        return new IfStmt(expr, body, ifWord.getLocation());
    }


    private Node parse() {
        Token token = tokens.get();
        switch(token.getType()) {
            case INTEGER, FLOAT, BINARY_OPERATOR -> {
                return parseNumberExpr();
            }
            case OPEN_BRACKET -> {
                return parseArrDecl();
            }
            case STRING -> {
                return parseString();
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
            case BOOLEAN -> {
                Token tkn = this.tokens.advance();
                return new BasicLit(tkn.getValue(), LiteralKind.BOOLEAN, tkn.getLocation());
            }
            case NULL -> {
                Token tkn = this.tokens.advance();
                return new BasicLit(tkn.getValue(), LiteralKind.NULL, tkn.getLocation());
            }
            case IF -> {
                return parseIfStmt();
            }
        }
        throw new ShipSyntaxError("Unexpected token ", token.getValue() , token.getLocation());

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
