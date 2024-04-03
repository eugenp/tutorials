package com.baeldung.infixpostfix;

import java.util.Stack;

public class InfixToPostFixExpressionConversion {

    private int getPrecedenceScore(char ch) {
        switch (ch) {
        case '^':
            return 3;

        case '*':
        case '/':
            return 2;

        case '+':
        case '-':
            return 1;
        }
        return -1;
    }

    private boolean isOperand(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9');
    }

    private char associativity(char ch) {
        if (ch == '^')
            return 'R';
        return 'L';
    }

    public String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (isOperand(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && (operatorPrecedenceCondition(infix, i, stack))) {
                    result.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private boolean operatorPrecedenceCondition(String infix, int i, Stack<Character> stack) {
        return getPrecedenceScore(infix.charAt(i)) < getPrecedenceScore(stack.peek()) || getPrecedenceScore(infix.charAt(i)) == getPrecedenceScore(stack.peek()) && associativity(infix.charAt(i)) == 'L';
    }
}
