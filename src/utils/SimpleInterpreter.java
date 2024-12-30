package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SimpleInterpreter {

    // Data structure to store variable assignments
    private static final Map<String, Integer> variables = new HashMap<>();

    // Utility method to evaluate arithmetic expressions
    public static int evaluateExpression(String expr) throws IllegalArgumentException {
        // Handles basic arithmetic: +, -, *, /, %
        String[] tokens = expr.split(" ");
        Stack<Integer> stack = new Stack<>();
        char operator = ' ';

        for (String token : tokens) {
            try {
                // If the token is an integer, push it onto the stack
                int value = Integer.parseInt(token);
                stack.push(value);
            } catch (NumberFormatException e) {
                // Otherwise, handle operators
                if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%")) {
                    operator = token.charAt(0);
                } else {
                    throw new IllegalArgumentException("Invalid operator: " + token);
                }
            }
        }

        // Perform the arithmetic operation
        if (stack.size() != 2 || operator == ' ') {
            throw new IllegalArgumentException("Invalid expression format");
        }

        int b = stack.pop();
        int a = stack.pop();
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '%' -> a % b;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    // Utility to process assignments (e.g., x = 5)
    public static void assignVariable(String variable, int value) {
        variables.put(variable, value);
    }

    // Utility to get a variable's value
    public static int getVariable(String variable) {
        if (variables.containsKey(variable)) {
            return variables.get(variable);
        } else {
            throw new IllegalArgumentException("Undefined variable: " + variable);
        }
    }

    // Method for evaluating if/else conditionals
    public static boolean evaluateCondition(String condition) {
        // Assume condition is in the format of "x > 5"
        String[] parts = condition.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid condition format");
        }

        int leftValue = getVariable(parts[0]);
        int rightValue = Integer.parseInt(parts[2]);

        return switch (parts[1]) {
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            default -> throw new IllegalArgumentException("Unsupported condition operator: " + parts[1]);
        };
    }

    // Method for while loop control flow
    public static void whileLoop(String condition, String[] commands) {
        while (evaluateCondition(condition)) {
            for (String command : commands) {
                executeCommand(command);
            }
        }
    }

    // Method to execute commands (assignments, conditionals, loops)
    public static void executeCommand(String command) {
        String[] tokens = command.split(" ");

        // Handle variable assignment (e.g., x = 10)
        if (tokens[1].equals("=")) {
            int value = Integer.parseInt(tokens[2]);
            assignVariable(tokens[0], value);
        }
        // Handle conditionals (e.g., if x > 5)
        else if (tokens[0].equals("if")) {
            String condition = command.substring(3);
            if (evaluateCondition(condition)) {
                System.out.println("Condition met: " + condition);
            }
        }
        // Handle loops (e.g., while x < 10)
        else if (tokens[0].equals("while")) {
            String condition = command.substring(6);
            String[] loopCommands = new String[]{"x = x + 1"}; // Example command for looping
            whileLoop(condition, loopCommands);
        }
        // Handle expressions (e.g., print x + 5)
        else if (tokens[0].equals("print")) {
            String expr = command.substring(6);
            int result = evaluateExpression(expr);
            System.out.println("Result: " + result);
        } else {
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    public static void main(String[] args) {
        // Example program: Sum of First N Numbers
        try {
            assignVariable("x", 0); // Sum of numbers
            assignVariable("n", 5); // Limit N to 5

            // While loop to sum numbers from 1 to n
            String[] loopCommands = new String[]{
                "x = x + n",
                "n = n - 1"
            };
            whileLoop("n > 0", loopCommands);

            // Print result
            System.out.println("Final sum: " + getVariable("x"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
