package com.baeldung.spring.cloud.stream.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@EnableTestBinder
@SpringBootTest
class LogEnrichApplicationUnitTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    void whenSendingLogMessage_thenItsEnrichedWithPrefix() {
        input.send(MessageBuilder.withPayload("hello world").build(), "queue.log.messages");

        Message<byte[]> message = output.receive(1000L, "queue.pretty.log.messages");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("[Baeldung] - hello world");
    }

    @Test
    void whenProcessingLongLogMessage_thenItsEnrichedWithPrefix() {
        input.send(MessageBuilder.withPayload("hello world").build(), "processLogs-in-0");

        Message<byte[]> message = output.receive(1000L, "queue.pretty.log.messages");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("[Baeldung] - hello world");
    }

    @Test
    void whenProcessingShortLogMessage_thenItsNotEnrichedWithPrefix() {
        input.send(MessageBuilder.withPayload("hello").build(), "processLogs-in-0");

        Message<byte[]> message = output.receive(1000L, "queue.pretty.log.messages");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("hello");
    }

    @Test
    void whenHighlightingLogMessage_thenItsTransformedToUppercase() {
        Message<String> msgIn = MessageBuilder.withPayload("hello")
                .setHeader("contentType", "text/plain")
                .build();
        input.send(msgIn, "highlightLogs-in-0");

        Message<byte[]> msgOut = output.receive(1000L, "highlightLogs-out-0");
        assertThat(msgOut.getPayload())
                .asString()
                .isEqualTo("HELLO");
    }
}