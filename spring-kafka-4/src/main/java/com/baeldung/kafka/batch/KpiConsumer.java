package com.baeldung.kafka.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

    public String getMessage() {
        return message;
    }

    private String message = "";
    @Autowired
    private DataLakeService dataLakeService;

    @KafkaListener(id = "kpi-listener", topics = "kpi_topic", containerFactory = "kafkaKpiListenerContainerFactory")
    public void listen(String record) throws InterruptedException {

        logger.info("messages received: {}", record);

        this.message = record;
        //pause the current thread and resume it when the count-down latch is reset to 0
        latch.await();

        List<String> messages = new ArrayList<>();
        messages.add(record);
        dataLakeService.save(messages);
        //reset the message
        latch = new CountDownLatch(1);
        message = "";
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
