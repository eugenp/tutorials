package com.baeldung.kafka.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KpiBatchConsumer {
    private final Logger logger = LoggerFactory.getLogger(KpiBatchConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    private DataLakeService dataLakeService;
    private List<String> receivedMessages = new ArrayList<>();

    @KafkaListener(id = "kpi-batch-listener", topics = "kpi_batch_topic", batch = "true", containerFactory = "kafkaKpiListenerContainerFactory")
    public void listen(ConsumerRecords<String, String> records) throws InterruptedException {
        logger.info("Number of elements in the records: {}", records.count());
        records.forEach(record -> receivedMessages.add(record.value()));

        latch.await();

        dataLakeService.save(receivedMessages);
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }
}
