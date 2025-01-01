import java.util.ArrayList;
import java.util.List;

class Parser {
    private final List<Token> tokens; // List of tokens produced by the lexer
    private int pos = 0; // Current position in the list of tokens

    /**
     * Constructor for the Parser.
     * @param tokens The list of tokens to parse.
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Returns the current token being processed. If the end of tokens is reached, returns an EOF token.
     * @return The current token.
     */
    public Token currentToken() {
        return pos < tokens.size() ? tokens.get(pos) : new Token(TokenType.EOF, "");
    }

    /**
     * Moves to the next token in the list.
     */
    private void advance() {
        if (pos < tokens.size()) {
            pos++;
        }
    }

    /**
     * Parses a factor, which is the most basic unit of an expression (e.g., a number, a variable, or a parenthesized expression).
     * @return The AST node representing the factor.
     * @throws RuntimeException if an unexpected token is encountered.
     */
    private Node parseFactor() {
        Token token = currentToken();
        if (token.type == TokenType.NUMBER) {
            advance();
            return new NumberNode(Integer.parseInt(token.value)); // Create a node for a number literal
        } else if (token.type == TokenType.IDENTIFIER) {
            String variableName = token.value;
            advance();
            return new VariableNode(variableName); // Create a node for a variable identifier
        } else if (token.value.equals("(")) {
            advance(); // Skip '('
            Node expression = parseExpression(); // Recursively parse the expression inside the parentheses
            if (!currentToken().value.equals(")")) {
                throw new RuntimeException("Expected ')'");
            }
            advance(); // Skip ')'
            return expression; // Return the parsed expression within the parentheses
        }
        throw new RuntimeException("Unexpected token in factor: " + token.value);
    }

    /**
     * Parses a term, which consists of factors combined with multiplication, division, or modulo operators.
     * Follows the order of operations (multiplication, division, modulo have higher precedence).
     * @return The AST node representing the term.
     */
    public Node parseTerm() {
        Node node = parseFactor(); // Start with a factor
        while (currentToken().value.equals("*") || currentToken().value.equals("/") || currentToken().value.equals("%")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseFactor()); // Create a binary operation node
        }
        return node;
    }

    /**
     * Parses a condition, which involves expressions compared using relational operators (e.g., <, >, <=, >=, ==, !=).
     * @return The AST node representing the condition.
     */
    private Node parseCondition() {
        Node node = parseExpression(); // Conditions can start with a general expression
        while (currentToken().value.equals("<=") || currentToken().value.equals(">=") ||
                currentToken().value.equals("<") || currentToken().value.equals(">") ||
                currentToken().value.equals("==") || currentToken().value.equals("!=")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseExpression()); // The right side of the comparison is also an expression
        }
        return node;
    }

    /**
     * Parses an expression, which consists of terms combined with addition or subtraction operators.
     * Follows the order of operations (addition and subtraction have lower precedence than multiplication, etc.).
     * @return The AST node representing the expression.
     */
    private Node parseExpression() {
        Node node = parseTerm(); // Start with a term
        while (currentToken().value.equals("+") || currentToken().value.equals("-")) {
            String operator = currentToken().value;
            advance();
            node = new BinaryOpNode(node, operator, parseTerm()); // Create a binary operation node
        }
        return node;
    }

    /**
     * Parses a statement, which can be a variable declaration, a while loop, an if statement, a print statement, an assignment, or a break statement.
     * This is the main entry point for parsing individual commands in the code.
     * @return The AST node representing the statement.
     * @throws RuntimeException if an unexpected token or syntax is encountered.
     */
    public Node parseStatement() {
        // Handle variable declarations (e.g., var x = 10;)
        if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("var")) {
            advance(); // Skip 'var' keyword
            if (currentToken().type != TokenType.IDENTIFIER) {
                throw new RuntimeException("Expected identifier after 'var'");
            }
            String variableName = currentToken().value;
            advance(); // Skip the identifier (variable name)
            if (!currentToken().value.equals("=")) {
                throw new RuntimeException("Expected '=' after variable name");
            }
            advance(); // Skip the '=' sign
            Node initializer = parseExpression(); // Parse the expression assigned to the variable
            return new VarDeclarationNode(variableName, initializer);
        }
        // Handle while statements (e.g., while (x > 0) { ... })
        else if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("while")) {
            advance(); // Skip 'while' keyword
            Node condition = parseCondition(); // Parse the condition of the while loop
            if (!currentToken().value.equals("{")) {
                throw new RuntimeException("Expected '{' after while condition");
            }
            advance(); // Skip the '{'
            List<Node> body = parseBlock(); // Parse the block of statements inside the while loop
            return new WhileNode(condition, body);
        }
        // Handle if statements (e.g., if (x > 0) { ... })
        else if (currentToken().type == TokenType.KEYWORD && currentToken().value.equals("if")) {
            advance(); // Skip 'if' keyword
            Node condition = parseCondition(); // Parse the condition of the if statement
            if (!currentToken().value.equals("{")) {
                throw new RuntimeException("Expected '{' after if condition");
            }
            advance(); // Skip the '{'
            List<Node> thenBranch = parseBlock(); // Parse the block of statements in the 'then' branch
            return new IfNode(condition, thenBranch);
        }
        // Handle print statements (e.g., print(x);)
        else if (currentToken().type == TokenType.IDENTIFIER && currentToken().value.equals("print")) {
            advance(); // Skip 'print' keyword
            if (!currentToken().value.equals("(")) {
                throw new RuntimeException("Expected '(' after 'print'");
            }
            advance(); // Skip the '('
            Node expression = parseCondition(); // Parse the expression to be printed
            if (!currentToken().value.equals(")")) {
                throw new RuntimeException("Expected ')' after print argument");
            }
            advance(); // Skip the ')'
            return new PrintNode(expression);
        }
        // Handle assignments (e.g., x = 10;)
        else if (currentToken().type == TokenType.IDENTIFIER && peek().value.equals("=")) {
            String variableName = currentToken().value;
            advance(); // Skip the identifier (variable name)
            advance(); // Skip the '=' sign
            Node expression = parseExpression(); // Parse the expression being assigned
            return new AssignmentNode(variableName, expression);
        }
        // Handle break statements (e.g., break;)
        else if (currentToken().type == TokenType.BREAK) {
            advance(); // Skip 'break' keyword
            return new BreakNode();
        }

        throw new RuntimeException(
                "Unexpected statement at token: " + currentToken().value +
                        " (type: " + currentToken().type + ") at position: " + pos
        );
    }

    /**
     * Parses a block of statements enclosed in curly braces '{' and '}'.
     * This is used for the body of loops and conditional statements.
     * @return A list of AST nodes representing the statements in the block.
     * @throws RuntimeException if an unexpected end of input is encountered or if a '}' is missing.
     */
    private List<Node> parseBlock() {
        List<Node> statements = new ArrayList<>();
        while (!currentToken().value.equals("}")) {
            if (currentToken().type == TokenType.EOF) {
                throw new RuntimeException("Unexpected end of input, missing '}'");
            }
            statements.add(parseStatement()); // Recursively parse each statement in the block
        }
        advance(); // Skip the closing '}'
        return statements;
    }

    /**
     * Peeks at the next token without advancing the current position.
     * @return The next token, or an EOF token if at the end of the list.
     */
    private Token peek() {
        if (pos + 1 < tokens.size()) {
            return tokens.get(pos + 1);
        }
        return new Token(TokenType.EOF, "");
    }
}
