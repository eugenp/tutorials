package com.baeldung.pattern.command.command;

@FunctionalInterface
public interface TextFileOperation {
    
    String execute();
    
}
