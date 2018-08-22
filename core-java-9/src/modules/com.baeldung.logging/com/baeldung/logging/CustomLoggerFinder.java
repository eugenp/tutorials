package com.baeldung.logging;

public class CustomLoggerFinder extends System.LoggerFinder {

    @Override
    public System.Logger getLogger(String name, Module module) {
        if ("ConsoleLogger".equals(name)) {
            return new ConsoleLogger();
        }
        throw new IllegalArgumentException(name + " module not found");
    }
}
