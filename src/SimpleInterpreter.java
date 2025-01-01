import java.util.List;
import java.util.function.BiConsumer; // Import BiConsumer

// The main class for the simple interpreter.
public class SimpleInterpreter {
    public static void main(String[] args) {
        // Helper function to execute code snippets.
        // Takes a name for the code snippet and the code itself.
        BiConsumer<String, String> runCode = (name, code) -> { // Change to BiConsumer
            System.out.println(name);
            System.out.println("```go"); // Display the code snippet with syntax highlighting (using "go" for example).
            System.out.println(code);
            System.out.println("```");
            // Create a lexer to tokenize the code.
            Lexer lexer = new Lexer(code);
            // Tokenize the code.
            List<Token> tokens = lexer.tokenize();
            // Create a parser to build the AST.
            Parser parser = new Parser(tokens);
            // Create an interpreter to execute the AST.
            Interpreter interpreter = new Interpreter();
            // Parse and execute statements until the end of the input.
            while (parser.currentToken().type != TokenType.EOF) {
                Node statement = parser.parseStatement();
                interpreter.execute(statement);
            }
            System.out.println("----------------------");
        };
        // Example usage of the interpreter with different code snippets.
        runCode.accept("1. Sum of First N Numbers", """
                var n = 10
                var sum = 0
                var i = 1
                while i <= n {
                    sum = sum + i
                    i = i + 1
                }
                print(sum)
                """);

        runCode.accept("2. Factorial of N", """
                var n = 5
                var factorial = 1
                var i = 1
                while i <= n {
                    factorial = factorial * i
                    i = i + 1
                }
                print(factorial)
                """);

        runCode.accept("3. GCD of Two Numbers", """
                var a = 48
                var b = 18
                while b != 0 {
                    var temp = b
                    b = a % b
                    a = temp
                }
                print(a)
                """);

        runCode.accept("4. Reverse a Number", """
                var number = 1234
                var reversed = 0
                while number > 0 {
                    var digit = number % 10
                    reversed = reversed * 10 + digit
                    number = number / 10
                }
                print(reversed)
                """);

        runCode.accept("5. Check if a Number is Prime", """
                var n = 13
                var isPrime = 1
                var i = 2
                while i * i <= n {
                    if n % i == 0 {
                        isPrime = 0
                        break
                    }
                    i = i + 1
                }
                if n <= 1 {
                    isPrime = 0
                }
                print(isPrime == 1)
                """);

        runCode.accept("6. Check if a Number is Palindrome", """
                var number = 121
                var temp = number
                var reversed = 0
                while temp > 0 {
                    var digit = temp % 10
                    reversed = reversed * 10 + digit
                    temp = temp / 10
                }
                print(number == reversed)
                """);

        runCode.accept("7. Find the Largest Digit in a Number", """
                var n = 3947
                var largestDigit = 0
                while n > 0 {
                    var digit = n % 10
                    if digit > largestDigit {
                        largestDigit = digit
                    }
                    n = n / 10
                }
                print(largestDigit)
                """);

        runCode.accept("8. Sum of Digits", """
                var number = 1234
                var sumOfDigits = 0
                while number > 0 {
                    var digit = number % 10
                    sumOfDigits = sumOfDigits + digit
                    number = number / 10
                }
                print(sumOfDigits)
                """);

        runCode.accept("9. Multiplication Table", """
                var number = 5
                var i = 1
                while i <= 10 {
                    print(number * i)
                    i = i + 1
                }
                """);

        runCode.accept("10. Nth Fibonacci Number", """
                var n = 10
                var a = 0
                var b = 1
                var count = 0
                while count < n - 1 {
                    var temp = a + b
                    a = b
                    b = temp
                    count = count + 1
                }
                print(b)
                """);
    }
}