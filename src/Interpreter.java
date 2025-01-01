import java.util.HashMap;
import java.util.Map;

// The Interpreter class is responsible for executing the Abstract Syntax Tree (AST).
class Interpreter {
    // A symbol table to store variables and their values.
    private final Map<String, Integer> symbolTable = new HashMap<>();

    // Executes a given AST node. This is the main entry point for interpreting the AST.
    // @param node The AST node to execute.
    public void execute(Node node) {
        // Handle variable declarations.
        if (node instanceof VarDeclarationNode varDec) {
            // Evaluate the initializer expression.
            int value = evaluate(varDec.initializer);
            // Store the variable and its value in the symbol table.
            symbolTable.put(varDec.variableName, value);
        }
        // Handle assignment operations.
        else if (node instanceof AssignmentNode assign) {
            // Evaluate the value to be assigned.
            int value = evaluate(assign.value);
            // Update the variable's value in the symbol table.
            symbolTable.put(assign.variable, value);
        }
        // Handle print statements.
        else if (node instanceof PrintNode printNode) {
            // Evaluate the expression to be printed.
            int result = evaluate(printNode.expression);
            // Print the result, handling boolean output (0 for false, 1 for true).
            if (result == 0) {
                System.out.println("false");
            } else if (result == 1) {
                System.out.println("true");
            } else {
                System.out.println(result); // For other non-boolean integer results
            }
        }
        // Handle while loops.
        else if (node instanceof WhileNode whileNode) {
            // Use a try-catch block to handle 'break' statements.
            try {
                // Continue looping as long as the condition evaluates to true.
                while (evaluateCondition(whileNode.condition)) {
                    // Execute each node in the body of the while loop.
                    for (Node bodyNode : whileNode.body) {
                        execute(bodyNode);
                    }
                }
            } catch (BreakException e) {
                // Catch the BreakException to exit the loop.
            }
        }
        // Handle if statements.
        else if (node instanceof IfNode ifNode) {
            // Evaluate the condition.
            if (evaluateCondition(ifNode.condition)) {
                // Execute each statement in the 'then' branch if the condition is true.
                for (Node statement : ifNode.thenBranch) {
                    execute(statement);
                }
            }
        }
        // Handle break statements.
        else if (node instanceof BreakNode) {
            // Throw a BreakException to exit the current loop.
            throw new BreakException();
        }
    }

    // Evaluates an AST node to produce an integer value.
    // @param node The AST node to evaluate.
    // @return The integer value of the evaluated node.
    private int evaluate(Node node) {
        // Handle number literal nodes.
        if (node instanceof NumberNode numberNode) {
            return numberNode.value;
        }
        // Handle variable nodes.
        else if (node instanceof VariableNode variableNode) {
            String variable = variableNode.name;
            // Check if the variable has been declared.
            if (!symbolTable.containsKey(variable)) {
                throw new RuntimeException("Variable not declared: " + variable);
            }
            // Retrieve the variable's value from the symbol table.
            return symbolTable.get(variable);
        }
        // Handle binary operation nodes.
        else if (node instanceof BinaryOpNode binOp) {
            // Evaluate the left and right operands.
            int left = evaluate(binOp.left);
            int right = evaluate(binOp.right);
            // Perform the operation based on the operator.
            return switch (binOp.operator) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                case "%" -> left % right;
                // Handle comparison operators, returning 1 for true and 0 for false.
                case "<=" -> left <= right ? 1 : 0;
                case ">=" -> left >= right ? 1 : 0;
                case "<" -> left < right ? 1 : 0;
                case ">" -> left > right ? 1 : 0;
                case "==" -> left == right ? 1 : 0;
                case "!=" -> left != right ? 1 : 0;
                default -> throw new RuntimeException("Unknown operator: " + binOp.operator);
            };
        }
        throw new RuntimeException("Unknown node type");
    }

    // Evaluates an AST node representing a condition to produce a boolean value.
    // @param node The AST node representing the condition.
    // @return True if the condition is met, false otherwise.
    private boolean evaluateCondition(Node node) {
        // Conditions are expected to be binary operations (comparisons).
        if (node instanceof BinaryOpNode binOp) {
            // Evaluate the left and right operands.
            int left = evaluate(binOp.left);
            int right = evaluate(binOp.right);
            // Evaluate the comparison based on the operator.
            return switch (binOp.operator) {
                case "<=" -> left <= right;
                case ">=" -> left >= right;
                case "<" -> left < right;
                case ">" -> left > right;
                case "==" -> left == right;
                case "!=" -> left != right;
                default -> throw new RuntimeException("Unknown comparison operator: " + binOp.operator);
            };
        }
        throw new RuntimeException("Cannot evaluate condition");
    }
}
