package com.baeldung.system;

/**
 * Note: This class is not meant for unit-testing since it uses system
 * features at low level and that it uses 'System' standard error stream
 * methods to show output on screen. Also unit-tests in CI environments
 * don't have console output for user to see messages. But the usage below
 * demonstrates how the methods can be used.
 */
public class SystemErrDemo {
    public static void main(String[] args) {
        // Print without 'hitting' return
        System.err.print("some inline error message");

        // Print and then 'hit' return
        System.err.println("an error message having new line at the end");
    }
}
