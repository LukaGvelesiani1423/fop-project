// Represents a 'print' statement node in the Abstract Syntax Tree (AST).
class PrintNode extends Node {
    // The AST node representing the expression to be printed.
    public Node expression;

    // Constructor for creating a PrintNode.
    // @param expression The expression node to print.
    public PrintNode(Node expression) {
        this.expression = expression;
    }
}