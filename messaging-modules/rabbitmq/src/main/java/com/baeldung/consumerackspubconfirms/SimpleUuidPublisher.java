package com.baeldung.consumerackspubconfirms;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;

public class SimpleUuidPublisher {

    private static final Logger log = LoggerFactory.getLogger(SimpleUuidPublisher.class);

    private final Channel channel;
    private final String queue;

    public SimpleUuidPublisher(Channel channel, String queue) {
        this.channel = channel;
        this.queue = queue;
    }

    public void send(String message) throws IOException {
        channel.basicPublish("", queue, null, message.getBytes());
        log.info("* sent {}", message);
    }
}