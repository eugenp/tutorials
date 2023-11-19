package com.baeldung.collections.stackreversal;

import com.baeldung.collections.sorting.Employee;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ReverseStackUsingQueue {
    public Stack reverseIntegerStack(Stack<Integer> inputStack) {
        Queue<Integer> queue = new LinkedList<>();
        while (!inputStack.isEmpty()) {
            queue.add(inputStack.pop());
        }
        while (!queue.isEmpty()) {
            inputStack.add(queue.remove());
        }
        return inputStack;
    }

    public Stack reverseStringStack(Stack<String> inputStack) {
        Queue<String> queue = new LinkedList<>();
        while (!inputStack.isEmpty()) {
            queue.add(inputStack.pop());
        }
        while (!queue.isEmpty()) {
            inputStack.add(queue.remove());
        }
        return inputStack;
    }

    public Stack reverseEmployeeStack(Stack<Employee> inputStack) {
        Queue<Employee> employeeQ = new LinkedList<>();
        while (!inputStack.isEmpty()) {
            employeeQ.add(inputStack.pop());
        }
        while (!employeeQ.isEmpty()) {
            inputStack.add(employeeQ.remove());
        }
        return inputStack;
    }
}
