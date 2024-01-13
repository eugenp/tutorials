package com.baeldung.netty.customhandlersandlisteners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.baeldung.netty.customhandlersandlisteners.model.Message;

public class MessageUnitTest {

    @Test
    public void whenBroadcastMessage_thenParsedSuccessfully() {
        String input = "Bob;Hello, world; go!";
        Message message = Message.parse(input);

        assertEquals("Bob", message.getUser());
        assertEquals("Hello, world; go!", message.getMessage());
        assertNotNull(message.getTime());
    }

    @Test
    public void whenNewMessage_thenExpectedFormat() {
        Message message = new Message("Alice", "Testing");
        Instant time = message.getTime();

        String expected = time + " - Alice: Testing";
        assertEquals(expected, message.toString());
    }
}
