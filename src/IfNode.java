import java.util.List;

// Represents a node in the Abstract Syntax Tree (AST) for an 'if' statement.
class IfNode extends Node {
    // The AST node representing the condition of the 'if' statement.
    public final Node condition;
    // A list of AST nodes representing the statements to execute if the condition is true.
    public final List<Node> thenBranch;

    // Constructor for creating an IfNode.
    // @param condition The condition node.
    // @param thenBranch The list of statements in the 'then' block.
    public IfNode(Node condition, List<Node> thenBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
    }
}