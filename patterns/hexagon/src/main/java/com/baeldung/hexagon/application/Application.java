package com.baeldung.hexagon.application;

import com.baeldung.hexagon.adapters.ClientIOAdapter;
import com.baeldung.hexagon.adapters.ClientInputAdapter;
import com.baeldung.hexagon.adapters.ClientOutputAdapter;
import com.baeldung.hexagon.adapters.MessageStore;
import com.baeldung.hexagon.core.ports.IMessageInput;
import com.baeldung.hexagon.core.ports.IMessageStore;
import com.baeldung.hexagon.core.services.MessagingService;

public class Application {

    private ClientOutputAdapter receiver;
    private IMessageInput poster;

    public Application() {
        receiver = new ClientOutputAdapter();
        IMessageStore store = new MessageStore();
        MessagingService service = new MessagingService(receiver, store);
        poster = new ClientInputAdapter(service);
    }

    public ClientIOAdapter registerConsoleClient(String userName, ClientOutputAdapter.MessageHandler handler) {
        return new ClientIOAdapter(poster, receiver, userName, handler);
    }
}
