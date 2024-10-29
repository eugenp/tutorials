package com.baeldung.kafka.batch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
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
@Import(KafkaKpiConsumerWithNoBatchConfig.class)
@ActiveProfiles("no-batch")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableKafka
@EmbeddedKafka(partitions = 1, topics = { "kpi_topic" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaNoBatchLiveTest {
    private final Logger logger = LoggerFactory.getLogger(KafkaNoBatchLiveTest.class);

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private KpiProducer kpiProducer;

    @Autowired
    private KpiConsumer kpiConsumer;
    
    @BeforeAll
    void setup() throws ExecutionException, InterruptedException {
        assertThat(embeddedKafka).isNotNull();
        publishMessages();
    }

    private void publishMessages() throws ExecutionException, InterruptedException {
        int count = 1;
        String messageTemplate = "Test KPI Message-";
        while(count <= 10) {
            logger.info("publishing message number {}", count);
            kpiProducer.sendMessage("kpi_topic", messageTemplate.concat(Integer.valueOf(count).toString()));
            count++;
        }
    }

    @RepeatedTest(10)
    void givenKafka_whenMessage1OnTopic_thenListenerConsumesMessages(RepetitionInfo repetitionInfo) {
        String testNo = String.valueOf(repetitionInfo.getCurrentRepetition());
        assertThat(kpiConsumer.getMessage().value()).isEqualTo("Test KPI Message-".concat(testNo));
        kpiConsumer.getLatch().countDown();
    }
}
