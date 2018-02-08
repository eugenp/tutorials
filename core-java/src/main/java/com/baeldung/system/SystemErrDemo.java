package com.baeldung.system;

public class SystemErrDemo {
    public static void main(String[] args) {
        System.err.print("some inline error message"); // The next message will start on same line after
        System.err.print(" using print(). "); // The next message will start on same line after
        System.err.println("an error message having new line at the end");
    }
}
