package error;


    //Purpose: ErrorReporter is the error tracker and printer.
    //Code: It will need code to:
    //Provide a method to report errors with line, column and message values
    //Keep a boolean variable to track if there are any errors.
    //Provide a method to check if there are any errors.
    // Package for handling errors


import java.util.HashMap;
import java.util.Scanner;
    public class ErrorReporter {
        private boolean hasErrors = false;

        // Reports an error with line, column, and message
        public void reportError(int line, int column, String message) {
            hasErrors = true;
            System.err.printf("Error at line %d, column %d: %s%n", line, column, message);
        }

        // Checks if any errors have been reported
        public boolean hasErrors() {
            return hasErrors;
        }
    }




     class Interpreter {
        private final HashMap<String, Object> variables = new HashMap<>();
        private final ErrorReporter errorReporter = new ErrorReporter();

        public void interpret(String program) {
            String[] lines = program.split("\n");

            for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
                String line = lines[lineNumber].trim();

                if (line.isEmpty()) continue; // Skip empty lines

                try {
                    if (line.startsWith("var ")) {
                        handleVariableDeclaration(line, lineNumber);
                    } else if (line.startsWith("fmt.Println(")) {
                        handlePrintStatement(line, lineNumber);
                    } else {
                        errorReporter.reportError(lineNumber + 1, 1, "Unsupported statement: " + line);
                    }
                } catch (Exception e) {
                    errorReporter.reportError(lineNumber + 1, 1, e.getMessage());
                }
            }

            if (errorReporter.hasErrors()) {
                System.err.println("Execution halted due to errors.");
            }
        }

        private void handleVariableDeclaration(String line, int lineNumber) {
            String[] parts = line.substring(4).split("=");
            if (parts.length != 2) {
                errorReporter.reportError(lineNumber + 1, 1, "Invalid variable declaration.");
                return;
            }

            String varName = parts[0].trim();
            String value = parts[1].trim();

            // Validate variable name
            if (!varName.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                errorReporter.reportError(lineNumber + 1, 1, "Invalid variable name: " + varName);
                return;
            }

            if (value.startsWith("\"") && value.endsWith("\"")) {
                // String variable
                variables.put(varName, value.substring(1, value.length() - 1));
            } else {
                try {
                    // Integer variable
                    variables.put(varName, Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    errorReporter.reportError(lineNumber + 1, 1, "Invalid value for variable: " + value);
                }
            }
        }

        private void handlePrintStatement(String line, int lineNumber) {
            if (!line.endsWith(")")) {
                errorReporter.reportError(lineNumber + 1, line.length(), "Invalid print statement.");
                return;
            }

            String content = line.substring(12, line.length() - 1).trim();
            if (content.startsWith("\"") && content.endsWith("\"")) {
                // Print string literal
                System.out.println(content.substring(1, content.length() - 1));
            } else if (variables.containsKey(content)) {
                // Print variable value
                System.out.println(variables.get(content));
            } else {
                errorReporter.reportError(lineNumber + 1, 1, "Undefined variable: " + content);
            }
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your Go-like program (type 'END' to finish):");

            StringBuilder program = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                program.append(line).append("\n");
            }
            System.out.println(program.toString());

            Interpreter interpreter = new Interpreter();
            interpreter.interpret(program.toString());
        }
    }


