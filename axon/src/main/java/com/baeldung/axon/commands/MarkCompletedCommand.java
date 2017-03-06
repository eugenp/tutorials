package com.baeldung.axon.commands;


import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class MarkCompletedCommand {
 
    @TargetAggregateIdentifier
    private final String todoId;
 
    public MarkCompletedCommand(String todoId) {
        this.todoId = todoId;
    }
 
    public String getTodoId() {
        return todoId;
    }
}