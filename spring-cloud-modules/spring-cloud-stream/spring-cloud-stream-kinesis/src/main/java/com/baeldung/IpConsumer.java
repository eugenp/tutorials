package com.baeldung;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class IpConsumer {

    @StreamListener(Sink.INPUT)
    public void consume(String ip) {
        System.out.println(ip);
    }
}