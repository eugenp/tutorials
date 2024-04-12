package com.baeldung.collections.stackreversal;

import java.util.Stack;

public class ReverseStackUsingRecursion {
    public Stack<Integer> reverseIntegerStack(Stack<Integer> inputStack) {
        reverseStack(inputStack);
        return inputStack;
    }

    private void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int top = stack.pop();
        reverseStack(stack);
        insertBottom(stack, top);
    }

    private void insertBottom(Stack<Integer> stack, int value) {
        if (stack.isEmpty()) {
            stack.add(value);
        } else {
            int top = stack.pop();
            insertBottom(stack, value);
            stack.add(top);
        }
    }
}
