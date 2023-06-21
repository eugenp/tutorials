package com.baeldung.spring.kafka.topicsandpartitions;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class TemperatureConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "celcius-scale-topic", concurrency = "1", groupId = "celsius-scale-group")
    public void consumer1(ConsumerRecord<?, ?> consumerRecord) {
        System.out.printf("Consuming %s", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
    }

    @KafkaListener(topics = "celcius-scale-topic", concurrency = "1", groupId = "celsius-scale-group")
    public void consumer2(ConsumerRecord<?, ?> consumerRecord) {
        System.out.printf("Consuming %s", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
    }

    @KafkaListener(topics = "kelvin-scale-topic", concurrency = "1", groupId = "kelvin-scale-group")
    public void consumer3(ConsumerRecord<?, ?> consumerRecord) {
        System.out.printf("Consuming %s", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
