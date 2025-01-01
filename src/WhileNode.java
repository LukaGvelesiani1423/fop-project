import java.util.List;

// Represents a 'while' loop node in the Abstract Syntax Tree (AST).
class WhileNode extends Node {
    // The AST node representing the loop condition.
    public final Node condition;
    // A list of AST nodes representing the statements in the loop body.
    public final List<Node> body;

    // Constructor for creating a WhileNode.
    // @param condition The condition node.
    // @param body The list of statements in the loop body.
    public WhileNode(Node condition, List<Node> body) {
        this.condition = condition;
        this.body = body;
    }
}