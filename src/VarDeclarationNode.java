// Represents a variable declaration node in the Abstract Syntax Tree (AST).
class VarDeclarationNode extends Node {
    // The name of the variable being declared.
    public final String variableName;
    // The AST node representing the initial value of the variable.
    public final Node initializer;

    // Constructor for creating a VarDeclarationNode.
    // @param variableName The name of the variable.
    // @param initializer The initial value expression node.
    public VarDeclarationNode(String variableName, Node initializer) {
        this.variableName = variableName;
        this.initializer = initializer;
    }
}