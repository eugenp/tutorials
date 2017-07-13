package com.baeldung.axon.commands;


import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CreateMessageCommand {
 
    @TargetAggregateIdentifier
    private final String id;
    private final String text;
 
    public CreateMessageCommand(String id, String text) {
        this.id = id;
        this.text = text;
    }
 
    public String getId() {
        return id;
    }
 
    public String getText() {
        return text;
    }
}