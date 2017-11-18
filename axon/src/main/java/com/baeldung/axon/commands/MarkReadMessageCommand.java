package com.baeldung.axon.commands;


import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class MarkReadMessageCommand {
 
    @TargetAggregateIdentifier
    private final String id;
 
    public MarkReadMessageCommand(String id) {
        this.id = id;
    }
 
    public String getId() {
        return id;
    }
}