package com.baeldung.server;

import com.baeldung.shared.MessageService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.time.LocalDateTime;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MessageServiceImpl extends RemoteServiceServlet implements MessageService {

    public String sendMessage(String message) throws IllegalArgumentException {
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }

        return "Hello, " + message + "!<br><br> Time received: " + LocalDateTime.now();
    }

}
