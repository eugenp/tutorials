package com.baeldung.mockito.argumentmatchers;

import org.mockito.ArgumentMatcher;

public class MessageMatcher implements ArgumentMatcher<Message> {

    private Message left;

    public MessageMatcher(Message message) {
        this.left = message;
    }

    @Override
    public boolean matches(Message right) {
        return left.getFrom().equals(right.getFrom()) && 
            left.getTo().equals(right.getTo()) &&
            left.getText().equals(right.getText()) && 
            right.getDate() != null && right.getId() != null;
    }
}