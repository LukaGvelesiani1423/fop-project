// Represents a number literal node in the Abstract Syntax Tree (AST).
class NumberNode extends Node {
    // The integer value of the number.
    public int value;

    // Constructor for creating a NumberNode.
    // @param value The integer value.
    public NumberNode(int value) {
        this.value = value;
    }
}
