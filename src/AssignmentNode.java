// Represents a node in the Abstract Syntax Tree (AST) for an assignment operation.
class AssignmentNode extends Node {
    // The name of the variable being assigned to.
    public String variable;
    // The AST node representing the value to be assigned to the variable.
    public Node value;

    // Constructor for creating an AssignmentNode.
    // @param variable The name of the variable.
    // @param value The AST node representing the value.
    public AssignmentNode(String variable, Node value) {
        this.variable = variable;
        this.value = value;
    }
}
