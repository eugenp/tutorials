package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.core.ports.MessageInput;

public class ClientInputAdapter {

    private final MessageInput inputPort;

    public ClientInputAdapter(MessageInput inputPort) {
        this.inputPort = inputPort;
    }

    public void post(String sender, String receiver, String content) {
        inputPort.post(new MessageInput.PostMessageCommand(sender, receiver, content));
    }
}
