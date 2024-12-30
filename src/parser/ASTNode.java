package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Node {
    public abstract void execute(Map<String, Integer> context);
}

class NumberNode extends Node {
    private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        // No-op for number nodes
    }

    public int getValue() {
        return value;
    }
}

class VariableNode extends Node {
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        // No-op for variable nodes
    }

    public int getValue(Map<String, Integer> context) {
        return context.getOrDefault(name, 0);
    }

    public String getName() {
        return name;
    }
}

class BinaryOpNode extends Node {
    private Node left;
    private Node right;
    private String operator;

    public BinaryOpNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        left.execute(context);
        right.execute(context);
    }

    public int getValue(Map<String, Integer> context) {
        int leftValue = left instanceof NumberNode ? ((NumberNode) left).getValue() : ((VariableNode) left).getValue(context);
        int rightValue = right instanceof NumberNode ? ((NumberNode) right).getValue() : ((VariableNode) right).getValue(context);

        switch (operator) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                return leftValue / rightValue;
            case "%":
                return leftValue % rightValue;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
}

class AssignmentNode extends Node {
    private String variable;
    private Node expression;

    public AssignmentNode(String variable, Node expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        expression.execute(context);
        int value = expression instanceof NumberNode ? ((NumberNode) expression).getValue() : ((BinaryOpNode) expression).getValue(context);
        context.put(variable, value);
    }
}

class IfNode extends Node {
    private Node condition;
    private Node thenBranch;
    private Node elseBranch;

    public IfNode(Node condition, Node thenBranch, Node elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        condition.execute(context);
        int condValue = condition instanceof NumberNode ? ((NumberNode) condition).getValue() : ((BinaryOpNode) condition).getValue(context);

        if (condValue != 0) {
            thenBranch.execute(context);
        } else if (elseBranch != null) {
            elseBranch.execute(context);
        }
    }
}

class WhileNode extends Node {
    private Node condition;
    private Node body;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        while (true) {
            condition.execute(context);
            int condValue = condition instanceof NumberNode ? ((NumberNode) condition).getValue() : ((BinaryOpNode) condition).getValue(context);

            if (condValue == 0) break;

            body.execute(context);
        }
    }
}

class BlockNode extends Node {
    private List<Node> statements;

    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(Map<String, Integer> context) {
        for (Node statement : statements) {
            statement.execute(context);
        }
    }
}