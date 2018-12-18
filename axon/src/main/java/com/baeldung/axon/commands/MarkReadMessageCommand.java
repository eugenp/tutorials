package com.baeldung.axon.commands;

import java.util.Objects;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class MarkReadMessageCommand {

    @TargetAggregateIdentifier
    private final String id;

    public MarkReadMessageCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkReadMessageCommand that = (MarkReadMessageCommand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}