package com.baeldung.spring.kafka.shareconsumer.generator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baeldung.spring.kafka.shareconsumer.model.Event;

@ConditionalOnProperty(prefix = "data.generator", name = "enabled", havingValue = "true")
@Component
public class RecordGenerator {

    @Autowired
    KafkaTemplate<String, Event> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(RecordGenerator.class);

    @Scheduled(fixedRate = 1000)
    public void generate() {
        String key = UUID.randomUUID()
            .toString();
        log.info("Producing record with key {} ...", key);
        kafkaTemplate.send("stopic", key, new Event(System.currentTimeMillis(), UUID.randomUUID()
            .toString()));
    }

}
