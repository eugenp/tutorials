package com.baeldung.callstack;

public class RecursiveCallStackOverflow {
    static int depth = 0;

    public static void recursiveStackOverflow() {
        depth++;
        recursiveStackOverflow();
    }

    public static void main(String[] args) {
        try {
            recursiveStackOverflow();
        } catch (StackOverflowError e) {
            System.out.println("Maximum depth of the call stack is " + depth);
        }
    }

}
