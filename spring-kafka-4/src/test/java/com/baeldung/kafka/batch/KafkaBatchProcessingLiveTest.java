package com.baeldung.kafka.batch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(KafkaKpiConsumerWithBatchConfig.class)
@ActiveProfiles("batch")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableKafka
@EmbeddedKafka(partitions = 1, topics = { "kpi_batch_topic" }, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaBatchProcessingLiveTest {

    private final Logger logger = LoggerFactory.getLogger(KafkaBatchProcessingLiveTest.class);
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private KpiProducer kpiProducer;

    @Autowired
    private KpiBatchConsumer kpiBatchConsumer;

    @BeforeAll
    void setup() throws ExecutionException, InterruptedException {
        assertThat(embeddedKafka).isNotNull();
        publishMessages();
    }

    private void publishMessages() throws ExecutionException, InterruptedException {
        int count = 1;
        String messageTemplate = "Test KPI Message-";
        while (count <= 100) {
            logger.info("publishing message number {}", count);
            kpiProducer.sendMessage("kpi_batch_topic", messageTemplate.concat(Integer.valueOf(count).toString()));
            count++;
        }
    }

    @RepeatedTest(5)
    void givenKafka_whenMessagesOnTopic_thenListenerConsumesMessages() {
        int messageSize = kpiBatchConsumer.getReceivedMessages().size();
        logger.info("The message received by test {}", messageSize);
        assertThat(messageSize % 20).isEqualTo(0);
        kpiBatchConsumer.getLatch().countDown();
    }
}
