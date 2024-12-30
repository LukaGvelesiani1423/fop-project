package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Lexer {
    public static List<String> tokenize(String code) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(code, " \t\n\r\f()+-*/%={},;", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;

    }

    //Purpose: Lexer is the tokenizer, it breaks down the text into meaningful pieces.
    //Code: It will need code to:
    //Read the input string of the program character by character,
    // keeping track of the current line and column.
    //Implement methods to skip over white space and comments.
    //Implement methods to read integer and identifiers from the code.
    //Implement a method to identify the keywords from the code
    //Implement methods to recognize operators such as +,-,*,/,%,=,==, !=, <, >, <=, >=.
    //Create Token objects for each token it identifies, including the type,
    // value and line and column information.
    //Create a list of Token objects and return this list to be consumed by the Parser.
    //If the Lexer finds an unknown character, report an error using the ErrorReporter class.
}
