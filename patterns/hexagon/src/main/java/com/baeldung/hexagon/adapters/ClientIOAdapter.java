package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.core.ports.IMessageInput;

public class ClientIOAdapter {

    private IMessageInput input;
    private String userName;

    public ClientIOAdapter(IMessageInput input, ClientOutputAdapter output, String userName, ClientOutputAdapter.MessageHandler handler) {
        this.input = input;
        this.userName = userName;
        output.register(userName, handler);
    }

    public void post(String receiver, String message) {
        input.post(new IMessageInput.PostMessageCommand(userName, receiver, message));
    }
}
