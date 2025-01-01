class Token {
    // The type of the token.
    TokenType type;
    // The value of the token (e.g., "123", "+", "var").
    String value;

    // Constructor for creating a Token.
    // @param type The token type.
    // @param value The token value.
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Returns a string representation of the token.
    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}