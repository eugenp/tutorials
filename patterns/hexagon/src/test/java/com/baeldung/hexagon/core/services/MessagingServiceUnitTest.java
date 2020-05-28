package com.baeldung.hexagon.core.services;

import com.baeldung.hexagon.core.domain.Message;
import com.baeldung.hexagon.core.ports.IMessageOutput;
import com.baeldung.hexagon.core.ports.IMessageStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceUnitTest {

    private MessagingService sut;

    @Mock
    private IMessageOutput outputPort;
    @Mock
    private IMessageStore store;

    @BeforeEach
    void setUp() {
        sut = new MessagingService(outputPort, store);
    }

    @Test
    void givenMessage_whenSendMessage_thenDrivenPortsCalled() {
        String sender = "John";
        String receiver = "Bob";
        String content = "hello";
        Message message = new Message("id", sender, receiver, content);

        sut.sendMessage(message);

        IMessageOutput.ReceiveMessageCommand expectedCommand
                = new IMessageOutput.ReceiveMessageCommand(sender, receiver, content);
        Mockito.verify(outputPort).receive(expectedCommand);
        Mockito.verify(store).saveMessage(message);
    }
}
