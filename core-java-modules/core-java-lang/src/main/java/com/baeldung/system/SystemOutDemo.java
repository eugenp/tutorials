package com.baeldung.system;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Note: This class is not meant for unit-testing since it uses system
 * features at low level and that it uses 'System' standard output stream
 * methods to show output on screen. Also unit-tests in CI environments
 * don't have console output for user to see messages. But the usage below
 * demonstrates how the methods can be used.
 */
public class SystemOutDemo {

    public static void main(String[] args) throws FileNotFoundException {
        // Print without 'hitting' return
        System.out.print("some inline message");

        // Print and then 'hit' return
        System.out.println("a message having new line at the end");

        // Changes output stream to send messages to file.
        System.setOut(new PrintStream("file.txt"));
    }
}
