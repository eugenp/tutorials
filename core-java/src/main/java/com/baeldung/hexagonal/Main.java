package com.baeldung.hexagonal;
public class Main {
    public static void main(String[] args) {
        Display display = new ConsoleDisplay();
        SimpleLogger simpleLogger = SimpleLogger.getInstance(display);
        simpleLogger.info("Logging info message");
        simpleLogger.warn("Logging warning message");
        simpleLogger.error("Logging error message");
    }
}