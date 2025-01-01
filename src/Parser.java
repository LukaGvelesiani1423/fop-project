import java.util.ArrayList;
import java.util.List;

// The Parser class is responsible for building the Abstract Syntax Tree (AST) from a list of tokens.
class Parser {
    // The list of tokens to parse.
    private final List<Token> tokens;
    // The current position in the list of tokens.
    private int pos = 0;

    // Constructor for creating a Parser.
    // @param tokens The list of tokens.
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Returns the current token being processed.
    public Token currentToken() {
        return pos < tokens.size() ? tokens.get(pos) : new Token(TokenType.EOF, "");
    }

    // Advances to the next token in the list.
    private void advance() {
        if (pos < tokens.size()) {
            pos++;
        }
    }

    // Returns the next token without advancing.
    private Token peek() {
        if (pos + 1 < tokens.size()) {
            return tokens.get(pos + 1);
        }
        return new Token(TokenType.EOF, "");
    }

    // Parses a factor (numbers, identifiers, or parenthesized expressions).
    private Node parseFactor() {
        Token token = currentToken();
        // Handle number literals.
        if (token.type == TokenType.NUMBER) {
            advance();
            return new NumberNode(Integer.parseInt(token.value));
        }
        // Handle identifiers (variables).
        else if (token.type == TokenType.IDENTIFIER) {
            String variableName = token.value;
            advance();
            return new VariableNode(variableName);
        }
        // Handle parenthesized expressions.
        else if (token.value.equals("(")) {
            advance(); // Skip '('
            Node expression = parseExpression();
            if (!currentToken().value.equals(")")) {
                throw new RuntimeException("Expected ')'");
            }
            advance(); // Skip ')'
            return expression;
        }
        throw new RuntimeException("Unexpected token in factor: " + token.value);
    }

    // Parses a term (multiplication, division, modulo).
    public Node parseTerm() {
        Node node = parseFactor();
        // Handle *, /, and % operators.
        while (currentToken().value.equals("*") || currentToken().value.equals("/") || currentToken().value.equals("%")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseFactor());
        }
        return node;
    }

    // Parses a condition (comparison operations).
    private Node parseCondition() {
        Node node = parseExpression(); // Conditions can contain arithmetic expressions
        // Handle comparison operators.
        while (currentToken().value.equals("<=") || currentToken().value.equals(">=") ||
                currentToken().value.equals("<") || currentToken().value.equals(">") ||
                currentToken().value.equals("==") || currentToken().value.equals("!=")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseExpression()); // Right side is also an expression
        }
        return node;
    }

    // Parses an expression (addition and subtraction).
    private Node parseExpression() {
        Node node = parseTerm();
        // Handle + and - operators.
        while (currentToken().value.equals("+") || currentToken().value.equals("-")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseTerm());
        }
        return node;
    }

    // Parses a single statement.
    public Node parseStatement() {
        // Handle variable declarations.
        if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("var")) {
            advance(); // Skip 'var'
            if (currentToken().type != TokenType.IDENTIFIER) {
                throw new RuntimeException("Expected identifier after 'var'");
            }
            String variableName = currentToken().value;
            advance(); // Skip identifier
            if (!currentToken().value.equals("=")) {
                throw new RuntimeException("Expected '=' after variable name");
            }
            advance(); // Skip '='
            Node initializer = parseExpression();
            return new VarDeclarationNode(variableName, initializer);
        }
        // Handle while statements.
        else if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("while")) {
            advance(); // Skip 'while'
            Node condition = parseCondition(); // Use parseCondition for while
            if (!currentToken().value.equals("{")) {
                throw new RuntimeException("Expected '{' after while condition");
            }
            advance(); // Skip '{'
            List<Node> body = parseBlock(); // Now correctly delegates
            return new WhileNode(condition, body);
        }
        // Handle if statements.
        else if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("if")) {
            advance(); // Skip 'if'
            Node condition = parseCondition(); // Use parseCondition for if
            if (!currentToken().value.equals("{")) {
                throw new RuntimeException("Expected '{' after if condition");
            }
            advance(); // Skip '{'
            List<Node> thenBranch = parseBlock(); // Now correctly delegates
            return new IfNode(condition, thenBranch);
        }
        // Handle print statements.
        else if (currentToken().type == TokenType.IDENTIFIER && currentToken().value.equals("print")) {
            advance(); // Skip 'print'
            if (!currentToken().value.equals("(")) {
                throw new RuntimeException("Expected '(' after 'print'");
            }
            advance(); // Skip '('
            Node expression = parseExpression(); // Parse the expression to print
            if (!currentToken().value.equals(")")) {
                throw new RuntimeException("Expected ')' after print argument");
            }
            advance(); // Skip ')'
            return new PrintNode(expression);
        }
        // Handle assignments.
        else if (currentToken().type == TokenType.IDENTIFIER && peek().value.equals("=")) {
            String variableName = currentToken().value;
            advance(); // Skip identifier
            advance(); // Skip '='
            Node expression = parseExpression();
            return new AssignmentNode(variableName, expression);
        }
        // Handle break statements.
        else if (currentToken().type == TokenType.BREAK) {
            advance(); // Skip 'break'
            return new BreakNode();
        }

        throw new RuntimeException(
                "Unexpected statement at token: " + currentToken().value +
                        " (type: " + currentToken().type + ") at position: " + pos
        );
    }

    // Parses a block of statements enclosed in curly braces.
    private List<Node> parseBlock() {
        List<Node> statements = new ArrayList<>();
        // Continue parsing statements until the closing curly brace is encountered.
        while (!currentToken().value.equals("}")) {
            if (currentToken().type == TokenType.EOF) {
                throw new RuntimeException("Unexpected end of input, missing '}'");
            }
            statements.add(parseStatement());
        }
        advance(); // Skip '}'
        return statements;
    }
}