package com.baeldung.spring.cloud.stream.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.baeldung.spring.cloud.stream.rabbit.processor.MyProcessor;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class MultipleOutputsWithConditionsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleOutputsWithConditionsServiceApplication.class, args);
    }

    @Autowired
    private MyProcessor processor;

    @StreamListener(target = MyProcessor.INPUT, condition = "payload < 10")
    public void routeValuesToAnOutput(Integer val) {
        processor.anOutput()
            .send(message(val));
    }

    @StreamListener(target = MyProcessor.INPUT, condition = "payload >= 10")
    public void routeValuesToAnotherOutput(Integer val) {
        processor.anotherOutput()
            .send(message(val));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val)
            .build();
    }
}
