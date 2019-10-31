package com.baeldung.spring.data.redis.queue;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class RedisMessageSubscriber implements MessageListener {

    public static BlockingQueue<String> messages = new ArrayBlockingQueue<String>(100);

    public void onMessage(final Message message, final byte[] pattern) {
        messages.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}