package com.baeldung;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class IpProducer {

    @Autowired
    private Source source;

    @Scheduled(fixedDelay = 3000L)
    private void produce() {
        IntStream.range(1, 200).mapToObj(ipSuffix -> "192.168.0." + ipSuffix)
            .forEach(entry -> source.output().send(MessageBuilder.withPayload(entry).build()));
    }
}