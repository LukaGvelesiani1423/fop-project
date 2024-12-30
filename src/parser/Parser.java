package parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<String> tokens;
    private int current;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public int getCurrentIndex() {
        return current;
    }

    private String consume() {
        return tokens.get(current++);
    }

    public Node parse() {
        String token = consume();
        switch (token) {
            case "let":
                return parseAssignment();
            case "if":
                return parseIf();
            case "while":
                return parseWhile();
            default:
                throw new RuntimeException("Unknown statement: " + token);
        }
    }

    private Node parseAssignment() {
        String variable = consume();
        consume(); // skip '='
        Node expression = parseExpression();
        consume(); // skip ';'
        return new AssignmentNode(variable, expression);
    }

    private Node parseIf() {
        consume(); // skip '('
        Node condition = parseExpression();
        consume(); // skip ')'
        Node thenBranch = parseBlock();
        Node elseBranch = null;
        if ("else".equals(consume())) {
            elseBranch = parseBlock();
        }
        return new IfNode(condition, thenBranch, elseBranch);
    }

    private Node parseWhile() {
        consume(); // skip '('
        Node condition = parseExpression();
        consume(); // skip ')'
        Node body = parseBlock();
        return new WhileNode(condition, body);
    }

    private Node parseBlock() {
        consume(); // skip '{'
        List<Node> statements = new ArrayList<>();
        while (!"}".equals(consume())) {
            statements.add(parse());
        }
        return new BlockNode(statements);
    }

    private Node parseExpression() {
        return parseAddSub();
    }

    private Node parseAddSub() {
        Node left = parseMulDiv();
        while (true) {
            String token = consume();
            if ("+".equals(token)) {
                left = new BinaryOpNode(left, parseMulDiv(), token);
            } else if ("-".equals(token)) {
                left = new BinaryOpNode(left, parseMulDiv(), token);
            } else {
                current--;
                return left;
            }
        }
    }

    private Node parseMulDiv() {
        Node left = parsePrimary();
        while (true) {
            String token = consume();
            if ("*".equals(token)) {
                left = new BinaryOpNode(left, parsePrimary(), token);
            } else if ("/".equals(token)) {
                left = new BinaryOpNode(left, parsePrimary(), token);
            } else {
                current--;
                return left;
            }
        }
    }

    private Node parsePrimary() {
        String token = consume();
        if ("(".equals(token)) {
            Node expr = parseExpression();
            consume(); // skip ')'
            return expr;
        } else {
            try {
                return new NumberNode(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                return new VariableNode(token);
            }
        }
    }
}


