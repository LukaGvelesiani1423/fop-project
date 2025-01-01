import java.util.ArrayList;
import java.util.List;

class Lexer {
    // The input string to be tokenized.
    private final String input;
    // The current position in the input string.
    private int position = 0;

    // Constructor for creating a Lexer.
    // @param input The input string.
    public Lexer(String input) {
        this.input = input;
    }

    // Returns the character at the current position without advancing.
    private char peek() {
        if (position >= input.length()) {
            return '\0'; // End of input.
        }
        return input.charAt(position);
    }

    // Returns the character at the next position without advancing.
    private char peekNext() {
        if (position + 1 >= input.length()) {
            return '\0'; // End of input.
        }
        return input.charAt(position + 1);
    }

    // Returns the character at the current position and advances the position.
    private char advance() {
        char current = peek();
        position++;
        return current;
    }

    // Tokenizes the input string and returns a list of tokens.
    // @return A list of tokens.
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        // Iterate through the input string.
        while (position < input.length()) {
            char currentChar = peek();
            // Tokenize numbers.
            if (Character.isDigit(currentChar)) {
                StringBuilder number = new StringBuilder();
                while (Character.isDigit(peek())) {
                    number.append(advance());
                }
                tokens.add(new Token(TokenType.NUMBER, number.toString()));
            }
            // Tokenize identifiers and keywords.
            else if (Character.isLetter(currentChar)) {
                StringBuilder identifier = new StringBuilder();
                while (Character.isLetterOrDigit(peek())) {
                    identifier.append(advance());
                }
                String identifierStr = identifier.toString();
                // Check for keywords.
                if (identifierStr.equals("var") || identifierStr.equals("while") || identifierStr.equals("if")) {
                    tokens.add(new Token(TokenType.KEYWORD, identifierStr));
                } else if (identifierStr.equals("break")) {
                    tokens.add(new Token(TokenType.BREAK, identifierStr)); // Corrected line
                } else if (identifierStr.equals("print")) {
                    tokens.add(new Token(TokenType.IDENTIFIER, identifierStr)); // Treat 'print' as identifier for parsing
                }
                // Otherwise, it's a variable identifier.
                else {
                    tokens.add(new Token(TokenType.IDENTIFIER, identifierStr));
                }
            }
            // Tokenize operators and symbols.
            else if (currentChar == '+') {
                tokens.add(new Token(TokenType.PLUS, String.valueOf(advance())));
            } else if (currentChar == '-') {
                tokens.add(new Token(TokenType.MINUS, String.valueOf(advance())));
            } else if (currentChar == '*') {
                tokens.add(new Token(TokenType.STAR, String.valueOf(advance())));
            } else if (currentChar == '/') {
                tokens.add(new Token(TokenType.SLASH, String.valueOf(advance())));
            } else if (currentChar == '%') {
                tokens.add(new Token(TokenType.PERCENT, String.valueOf(advance())));
            } else if (currentChar == '(') {
                tokens.add(new Token(TokenType.LPAREN, String.valueOf(advance())));
            } else if (currentChar == ')') {
                tokens.add(new Token(TokenType.RPAREN, String.valueOf(advance())));
            } else if (currentChar == '{') {
                tokens.add(new Token(TokenType.LBRACE, String.valueOf(advance())));
            } else if (currentChar == '}') {
                tokens.add(new Token(TokenType.RBRACE, String.valueOf(advance())));
            }
            // Tokenize equals sign (either "=" or "==").
            else if (currentChar == '=') {
                if (peekNext() == '=') {
                    tokens.add(new Token(TokenType.EQUAL_EQUAL, "=="));
                    advance();
                    advance();
                } else {
                    tokens.add(new Token(TokenType.EQ, String.valueOf(advance())));
                }
            }
            // Tokenize not equals sign "!=".
            else if (currentChar == '!') {
                if (peekNext() == '=') {
                    tokens.add(new Token(TokenType.BANG_EQUAL, "!="));
                    advance();
                    advance();
                } else {
                    throw new RuntimeException("Unexpected character: " + currentChar);
                }
            }
            // Tokenize less than and less than or equal to.
            else if (currentChar == '<') {
                if (peekNext() == '=') {
                    tokens.add(new Token(TokenType.LESS_EQUAL, "<="));
                    advance();
                    advance();
                } else {
                    tokens.add(new Token(TokenType.LESS_THAN, "<"));
                    advance();
                }
            }
            // Tokenize greater than and greater than or equal to.
            else if (currentChar == '>') {
                if (peekNext() == '=') {
                    tokens.add(new Token(TokenType.GREATER_EQUAL, ">="));
                    advance();
                    advance();
                } else {
                    tokens.add(new Token(TokenType.GREATER_THAN, ">"));
                    advance();
                }
            }
            // Skip whitespace.
            else if (Character.isWhitespace(currentChar)) {
                advance();
            }
            // Handle unexpected characters.
            else {
                throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }
        // Add the end-of-file token.
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
