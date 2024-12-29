package main;

import lexer.Lexer;
import parser.ASTNode;
import parser.Parser;
import error.ErrorReporter;
import interpreter.GolangInterpreter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Main entry point of the interpreter
        // This class reads a Go program file, tokenizes, parses, and interprets it
        String fileName = "examples/sum_first_n.go"; // Default file, can be passed as a command line argument

    }
}