package com.baeldung.spring.cloud.stream.rabbit.v2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
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

    @Test
    void test() {
        input.send(MessageBuilder.withPayload("hello world").build(), "input-topic");

        Message<byte[]> message = output.receive(1000L, "output-topic");

        assertThat(message.getPayload())
                .asString()
                .isEqualTo("HELLO WORLD");
    }
}