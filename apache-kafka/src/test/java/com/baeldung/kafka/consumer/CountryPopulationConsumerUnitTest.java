package com.baeldung.kafka.consumer;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryPopulationConsumerUnitTest {

    private static final String TOPIC = "topic";
    private static final int PARTITION = 0;

    private CountryPopulationConsumer countryPopulationConsumer;

    private List<CountryPopulation> updates;
    private Throwable pollException;

    private MockConsumer<String, Integer> consumer;

    @BeforeEach
    void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        updates = new ArrayList<>();
        countryPopulationConsumer = new CountryPopulationConsumer(consumer, ex -> this.pollException = ex, updates::add);
    }

    @Test
    void whenStartingByAssigningTopicPartition_thenExpectUpdatesAreConsumedCorrectly() {
        // GIVEN
        consumer.schedulePollTask(() -> consumer.addRecord(record(TOPIC, PARTITION, "Romania", 19_410_000)));
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startByAssigning(TOPIC, PARTITION);

        // THEN
        assertThat(updates).hasSize(1);
        assertThat(consumer.closed()).isTrue();
    }

    @Test
    void whenStartingBySubscribingToTopic_thenExpectUpdatesAreConsumedCorrectly() {
        // GIVEN
        consumer.schedulePollTask(() -> {
            consumer.rebalance(Collections.singletonList(new TopicPartition(TOPIC, 0)));
            consumer.addRecord(record(TOPIC, PARTITION, "Romania", 19_410_000));
        });
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startBySubscribing(TOPIC);

        // THEN
        assertThat(updates).hasSize(1);
        assertThat(consumer.closed()).isTrue();
    }

    @Test
    void whenStartingBySubscribingToTopicAndExceptionOccurs_thenExpectExceptionIsHandledCorrectly() {
        // GIVEN
        consumer.schedulePollTask(() -> consumer.setPollException(new KafkaException("poll exception")));
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, 0);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startBySubscribing(TOPIC);

        // THEN
        assertThat(pollException).isInstanceOf(KafkaException.class).hasMessage("poll exception");
        assertThat(consumer.closed()).isTrue();
    }

    private ConsumerRecord<String, Integer> record(String topic, int partition, String country, int population) {
        return new ConsumerRecord<>(topic, partition, 0, country, population);
    }
}