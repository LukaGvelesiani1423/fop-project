package lexer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int column;

    // Constructor to initialize token values
    public Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    } // Getters for token properties

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    } // toString method for debugging

    @Override
    public String toString() {
        return String.format("Token[type=%s, value='%s', line=%d, column=%d]", type, value, line, column);
    }


    public enum TokenType {
        INTEGER,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        MODULUS,
        IF,
        ELSE,
        WHILE,
        IDENTIFIER,
        ASSIGN,
        EQ,
        NEQ,
        LT,
        GT,
        LTE,
        GTE,
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        SEMICOLON,
        EOF
    }
}


