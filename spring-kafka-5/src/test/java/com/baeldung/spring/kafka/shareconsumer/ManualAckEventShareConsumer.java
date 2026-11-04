package com.baeldung.spring.kafka.shareconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.ShareAcknowledgment;

import com.baeldung.spring.kafka.shareconsumer.exception.TransientException;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

public class ManualAckEventShareConsumer {

    private static final Logger log = LoggerFactory.getLogger(ManualAckEventShareConsumer.class);

    @KafkaListener(topics = "stopic", groupId = "shgroup", containerFactory = "shareKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, Event> record, ShareAcknowledgment acknowledgment) {
        try {
            doConsume(record);
            acknowledgment.acknowledge();
        } catch (TransientException e) {
            acknowledgment.release();
        } catch (RuntimeException e) {
            acknowledgment.reject();
        } finally {
            acknowledgment.reject();
        }
    }

    private void doConsume(ConsumerRecord<String, Event> record) {
        if (record.value()
            .id() < 0L || record.value()
            .id() % 2 == 0) {
            // Happy scenario
            log.info("Acknowledging event {}", record.value());
        } else if (record.value()
            .id() % 3 == 0) {
            // Simulating transient error
            throw new TransientException("Transient error: %d".formatted(record.value()
                .id()));
        } else {
            // Simulating non-transient error
            throw new RuntimeException("Something went wrong!");
        }
    }

}
