package com.baeldung.spring.cloud.stream.rabbit.v2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SampleApplication.class)
@EnableTestBinder
class SampleApplicationTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Autowired
    private StreamBridge streamBridge;

    @Test
    void simple() {
        input.send(MessageBuilder.withPayload("hello world").build(), "input-topic");

        Message<byte[]> message = output.receive(1000L, "output-topic");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("[Baeldung] - hello world");
    }

    @Test
    void streamBridge() {
        streamBridge.send("input-topic", MessageBuilder.withPayload("hello world").build());

        Message<byte[]> message = output.receive(1000L, "output-topic");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("[Baeldung] - hello world");
    }

    @Test
    void multipleFunctionsAndFunctionComposition() {
        //temp example
//        input.send(MessageBuilder.withPayload("hello world").build(), "highlightMessage-in-0");
//
//        Message<byte[]> message = output.receive(1000L, "highlightMessage-out-0");
//
//        assertThat(message.getPayload())
//                .asString()
//                .endsWith("HELLO WORLD");

        // to transition into:
        // #    function.definition: enrichLogMessage;highlightMessage --> enrichLogMessage;highlightMessage|enrichLogMessage

        input.send(MessageBuilder.withPayload("hello world").build(), "input-topicX");

        Message<byte[]> message = output.receive(1000L, "output-topicX");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("[Baeldung] - HELLO WORLD");
    }

    @Test
    void eventRouting_fromProducer() {
        //s.c.s.function.routing.enabled: true
        input.send(MessageBuilder
                .withPayload("hello world")
                .setHeader("spring.cloud.function.definition", "enrichLogMessage")
                //or .setHeader(spring.cloud.function.routing-expression, *SpEL expression*)
                .build());
        // or based on application properties:
        //     spring.cloud.function.routing-expression=headers['type'] SpEL where Message is root object
        //     spring.cloud.function.function.definition=enrichLogMessage


        Message<byte[]> message = output.receive(1000L, "output-topic");

        assertThat(message.getPayload())
                .asString()
                .endsWith("[Baeldung] - hello world");
    }

    @Test
    void eventRouting_fromConsumer() {
        input.send(MessageBuilder.withPayload("hello world").build(), "processLogs-in-0");
        Message<byte[]> out1 = output.receive(1000L, "output-topic");
        assertThat(out1.getPayload())
                .asString()
                .endsWith("[Baeldung] - hello world");

        input.send(MessageBuilder.withPayload("hello").build(), "processLogs-in-0");
        Message<byte[]> out2 = output.receive(1000L, "output-topic");
        assertThat(out2.getPayload())
                .asString()
                .endsWith("hello");
    }

}