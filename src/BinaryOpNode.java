// Represents a node in the Abstract Syntax Tree (AST) for a binary operation.
class BinaryOpNode extends Node {
    // The AST node representing the left operand.
    public Node left;
    // The operator of the binary operation (e.g., "+", "-", "*").
    public String operator;
    // The AST node representing the right operand.
    public Node right;

    // Constructor for creating a BinaryOpNode.
    // @param left The left operand node.
    // @param operator The operator string.
    // @param right The right operand node.
    public BinaryOpNode(Node left, String operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}