package com.baeldung.kafka.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("no-batch")
public class KpiConsumer {
    private final Logger logger = LoggerFactory.getLogger(KpiConsumer.class);
    private CountDownLatch latch = new CountDownLatch(1);

    private ConsumerRecord<String, String> message;
    @Autowired
    private DataLakeService dataLakeService;

    @KafkaListener(id = "kpi-listener", topics = "kpi_topic", containerFactory = "kafkaKpiListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) throws InterruptedException {

        logger.info("messages received: {}", record.value());

        this.message = record;
        //pause the current thread and resume it when the count-down latch is reset to 0
        latch.await();

        List<String> messages = new ArrayList<>();
        messages.add(record.value());
        dataLakeService.save(messages);
        //reset the latch
        latch = new CountDownLatch(1);
    }

    public ConsumerRecord<String, String> getMessage() {
        return message;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
