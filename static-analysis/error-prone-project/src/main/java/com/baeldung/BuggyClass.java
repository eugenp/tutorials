package com.baeldung;

public class BuggyClass {
    public static void main(String[] args) {
        if (args.length == 0 || args[0] != null) {
            new IllegalArgumentException();
        }
    }

    public void emptyMethod() {
    }
}
