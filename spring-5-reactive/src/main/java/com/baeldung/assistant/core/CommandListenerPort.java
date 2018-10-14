package com.baeldung.assistant;

public interface CommandListenerPort {
    void onUserCommand(String text);
}
