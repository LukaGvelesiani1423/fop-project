// Enumerates the different types of tokens.
internal enum class TokenType {
    NUMBER,       // Represents numeric literals.
    IDENTIFIER,   // Represents variable names and the 'print' keyword.
    PLUS,         // Represents the '+' operator.
    MINUS,        // Represents the '-' operator.
    STAR,         // Represents the '*' operator.
    SLASH,        // Represents the '/' operator.
    PERCENT,      // Represents the '%' operator.
    EQ,           // Represents the '=' assignment operator.
    LPAREN,       // Represents the '(' left parenthesis.
    RPAREN,       // Represents the ')' right parenthesis.
    LBRACE,       // Represents the '{' left brace.
    RBRACE,       // Represents the '}' right brace.
    KEYWORD,      // Represents language keywords (e.g., 'var', 'while', 'if').
    BREAK,        // Represents the 'break' keyword.
    EOF,          // Represents the end of the input.
    LESS_EQUAL,   // Represents the '<=' operator.
    GREATER_EQUAL,// Represents the '>=' operator.
    LESS_THAN,    // Represents the '<' operator.
    GREATER_THAN, // Represents the '>' operator.
    EQUAL_EQUAL,  // Represents the '==' operator.
    BANG_EQUAL    // Represents the '!=' operator.
}
