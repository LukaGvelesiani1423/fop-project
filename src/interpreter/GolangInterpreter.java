package interpreter;



        //Purpose: GolangInterpreter is the code executor, it gives meaning to the AST.
        //Code: It will need code to:
        //Traverse the AST nodes by means of a visit method,
        // calling different methods depending on the type of the node
        //Implement specific logic for PROGRAM nodes, ASSIGNMENT
        // nodes, BINARY_OPERATION nodes, UNARY_OPERATION nodes,
        // IF_STATEMENT nodes, WHILE_STATEMENT nodes, PRINT_STATEMENT
        // nodes, and BLOCK nodes.
        //Create a symbolTable to store variables and their values.
        //Evaluate expressions recursively by means of an
        // evaluateExpression method, calling different methods to
        // evaluate different types of expressions, for example,
        // integers, variables, binary or unary operations.
        //Handle +,-,*,/,%,==,!=,<,>,<=,>= operations in the code.
        //Use the ErrorReporter class to report any runtime error,
        // such as division by zero, or undefined variables.






import error.ErrorReporter;

import java.util.HashMap;
import java.util.Map;

public class GolangInterpreter {
    private final ErrorReporter errorReporter = new ErrorReporter();
    private final Map<String, Object> symbolTable = new HashMap<>();

    public static void main(String[] args) {
        GolangInterpreter interpreter = new GolangInterpreter();
        interpreter.executeProgram();
    }

    /**
     * Executes the program (example implementation).
     */
    public void executeProgram() {
        // Example assignment: x = 5 + 3
        traverseAssignment("x", evaluateExpression(new BinaryOperationNode(
                new IntegerNode(5),
                new IntegerNode(3),
                "+"
        )));

        // Example: Print x
        traversePrintStatement(new VariableNode("x"));
    }

    /**
     * Handles variable assignment.
     */
    public void traverseAssignment(String variableName, Object value) {
        symbolTable.put(variableName, value);
    }

    /**
     * Handles print statements.
     */
    public void traversePrintStatement(ExpressionNode node) {
        Object value = evaluateExpression(node);
        System.out.println(value);
    }

    /**
     * Evaluates expressions recursively.
     */
    public Object evaluateExpression(ExpressionNode node) {
        if (node instanceof IntegerNode) {
            return ((IntegerNode) node).getValue();
        } else if (node instanceof VariableNode) {
            String varName = ((VariableNode) node).getName();
            if (!symbolTable.containsKey(varName)) {
                errorReporter.reportError(0, 0, "Undefined variable: " + varName);
                return null;
            }
            return symbolTable.get(varName);
        } else if (node instanceof BinaryOperationNode) {
            BinaryOperationNode binaryNode = (BinaryOperationNode) node;
            Object left = evaluateExpression(binaryNode.getLeft());
            Object right = evaluateExpression(binaryNode.getRight());

            if (left instanceof Integer && right instanceof Integer) {
                return evaluateBinaryOperation((int) left, (int) right, binaryNode.getOperator());
            } else {
                errorReporter.reportError(0, 0, "Invalid binary operation operands.");
                return null;
            }
        }

        errorReporter.reportError(0, 0, "Unknown expression node.");
        return null;
    }

    /**
     * Handles binary operations.
     */
    public int evaluateBinaryOperation(int left, int right, String operator) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    errorReporter.reportError(0, 0, "Division by zero.");
                    return 0;
                }
                return left / right;
            case "%":
                return left % right;
            default:
                errorReporter.reportError(0, 0, "Unsupported operator: " + operator);
                return 0;
        }
    }

    // Node interfaces and implementations
    interface ExpressionNode {}

    class IntegerNode implements ExpressionNode {
        private final int value;

        public IntegerNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class VariableNode implements ExpressionNode {
        private final String name;

        public VariableNode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class BinaryOperationNode implements ExpressionNode {
        private final ExpressionNode left;
        private final ExpressionNode right;
        private final String operator;

        public BinaryOperationNode(ExpressionNode left, ExpressionNode right, String operator) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }

        public ExpressionNode getLeft() {
            return left;
        }

        public ExpressionNode getRight() {
            return right;
        }

        public String getOperator() {
            return operator;
        }
    }
}



