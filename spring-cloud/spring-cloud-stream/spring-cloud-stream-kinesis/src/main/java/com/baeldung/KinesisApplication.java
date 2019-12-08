package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Processor.class)
public class KinesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinesisApplication.class, args);
    }

    @Autowired
    private Processor processor;

    @StreamListener(Processor.INPUT)
    public void consume(String val) {
        System.out.println(val);
    }

    public void produce(String val) {
        processor.output().send(MessageBuilder.withPayload(val).build());
    }
}