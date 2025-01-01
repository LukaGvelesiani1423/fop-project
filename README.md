# Simple Interpreter

A basic interpreter for a simple imperative programming language. This interpreter supports variable declarations, assignments, arithmetic operations, conditional statements (`if`), looping (`while`), and a `break` statement.

## Team Members and Roles

- **Luka Aghlemashvili:** Editor, who Commits and unites everything.
- **Giorgi Jimshelishvili:** [Teammate's Role]
- **Tamar Matitashvili:** [Teammate's Role]
- **Luka Gveseliani:** [Another Teammate's Role]

## Instructions for Setting Up and Running the Project

1. **Prerequisites:**
   -   Ensure you have Java Development Kit (JDK) installed on your system. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source distribution like [OpenJDK](https://openjdk.org/).

2. **Compilation:**
   -   Save all the `.java` files in the same directory.
   -   Open your terminal or command prompt and navigate to that directory.
   -   Compile the Java files using the `javac` command:
       ```bash
       javac *.java
       ```
       This will generate `.class` files for each `.java` file.

3. **Running the Interpreter:**
   -   After successful compilation, run the `SimpleInterpreter` class using the `java` command:
       ```bash
       java SimpleInterpreter
       ```
   -   The interpreter will execute predefined code snippets and print their output to the console.

## Features and Modules

### 1. Abstract Syntax Tree (AST) Nodes

-   **`AssignmentNode.java`:** Represents an assignment operation (e.g., `variable = value`).
-   **`BinaryOpNode.java`:** Represents a binary operation (e.g., `left + right`).
-   **`BreakNode.java`:** Represents a `break` statement.
-   **`IfNode.java`:** Represents an `if` conditional statement.
-   **`Node.java`:** Abstract base class for all AST nodes.
-   **`NumberNode.java`:** Represents a numeric literal.
-   **`PrintNode.java`:** Represents a `print` statement.
-   **`VarDeclarationNode.java`:** Represents a variable declaration (e.g., `var x = 10`).
-   **`VariableNode.java`:** Represents a variable identifier.
-   **`WhileNode.java`:** Represents a `while` loop.

### 2. Interpreter

-   **`Interpreter.java`:** Executes the AST. It maintains a symbol table to store variable values and provides methods to `execute` AST nodes and `evaluate` expressions.

### 3. Lexer

-   **`Lexer.java`:** Tokenizes the input code string. It reads the input character by character and groups them into meaningful tokens like numbers, identifiers, operators, and keywords.

### 4. Parser

-   **`Parser.java`:** Builds the AST from the stream of tokens produced by the lexer. It follows the grammar of the simple language to create a hierarchical representation of the code.

### 5. Main Application

-   **`SimpleInterpreter.java`:** Contains the `main` method to demonstrate the interpreter. It includes example code snippets that are parsed and executed.

### 6. Token and Token Types

-   **`Token.java`:** Represents a single token with its type and value.
-   **`TokenType.java`:** Enumerates all possible types of tokens in the language.

### 7. Exceptions

-   **`BreakException.java`:** A custom exception used to handle `break` statements within loops.

### 8