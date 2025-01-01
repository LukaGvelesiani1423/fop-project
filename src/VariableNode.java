// Represents a variable node in the Abstract Syntax Tree (AST).
class VariableNode extends Node {
    // The name of the variable.
    public String name;

    // Constructor for creating a VariableNode.
    // @param name The name of the variable.
    public VariableNode(String name) {
        this.name = name;
    }
}
