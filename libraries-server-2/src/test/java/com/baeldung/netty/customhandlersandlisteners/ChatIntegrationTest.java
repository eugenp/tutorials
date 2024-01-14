package com.baeldung.netty.customhandlersandlisteners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.baeldung.netty.customhandlersandlisteners.handler.ClientEventHandler;
import com.baeldung.netty.customhandlersandlisteners.handler.ServerEventHandler;

import io.netty.channel.embedded.EmbeddedChannel;

class ChatIntegrationTest {

    private static final String MSG_1 = "Alice;Anyone there?!";
    private static final String MSG_2 = "Bob;Hi, Alice!";

    @Test
    void whenMessagesWrittenToServer_thenMessagesConsumed() {
        EmbeddedChannel server = new EmbeddedChannel(new ServerEventHandler());

        assertTrue(server.writeOutbound(MSG_1));
        assertTrue(server.writeOutbound(MSG_2));

        assertEquals(2, server.outboundMessages()
            .size());

        assertEquals(MSG_1, server.readOutbound()
            .toString());
        assertEquals(MSG_2, server.readOutbound()
            .toString());

        server.close();
    }

    @Test
    void whenClientReceivesMessages_thenMessagesConsumed() {
        EmbeddedChannel client = new EmbeddedChannel(new ClientEventHandler());

        assertTrue(client.writeOutbound(MSG_1));
        assertTrue(client.writeOutbound(MSG_2));

        assertEquals(2, client.outboundMessages()
            .size());

        assertEquals(MSG_1, client.readOutbound()
            .toString());
        assertEquals(MSG_2, client.readOutbound()
            .toString());

        client.close();
    }
}
