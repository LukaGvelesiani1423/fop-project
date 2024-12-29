package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
public class Token {
    public static List<String> tokenize(String code) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(code, " \t\n\r\f()+-*/%={},;", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (!token.isEmpty()) { tokens.add(token);
            }
        }
        return tokens;
    }
}

