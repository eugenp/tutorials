package com.baeldung.hexagon.application;

import com.baeldung.hexagon.adapters.ClientInputAdapter;
import com.baeldung.hexagon.adapters.ClientOutputAdapter;
import com.baeldung.hexagon.adapters.MessageStore;
import com.baeldung.hexagon.core.ports.IMessageStore;
import com.baeldung.hexagon.core.ports.MessageInput;
import com.baeldung.hexagon.core.services.MessagingService;

public class Application {

    private ClientOutputAdapter receiver;
    private ClientInputAdapter poster;

    public Application() {
        receiver = new ClientOutputAdapter();
        IMessageStore store = new MessageStore();
        MessagingService service = new MessagingService(receiver, store);
        MessageInput input = new MessageInput(service);
        poster = new ClientInputAdapter(input);
    }

    public void registerConsoleClient(String userName, ClientOutputAdapter.MessageHandler handler) {
        receiver.register(userName, handler);
    }

    public void postMessage(String sender, String receiver, String content) {
        poster.post(sender, receiver, content);
    }
}
