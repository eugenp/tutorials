package com.baeldung.axon.gui;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.axon.coreapi.commands.CreateMessageCommand;
import com.baeldung.axon.coreapi.commands.MarkReadMessageCommand;

@RestController
public class MessagesRestEndpoint {

    private final CommandGateway commandGateway;

    public MessagesRestEndpoint(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/hello")
    public void publishMessages() {
        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(itemId, "Hello, how is your day? :-)"));
        commandGateway.send(new MarkReadMessageCommand(itemId));
    }

}
