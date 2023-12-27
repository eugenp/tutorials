package com.baeldung.kafka.consumer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class VariableFetchSizeKafkaListenerLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Test
    void whenChangingMaxPartitionFetchBytesProperty_thenAdjustBatchSizesWhilePolling() throws Exception {
        publishSensorData(300, "engine.sensors.temperature");

        // max.partition.fetch.bytes = 500 Bytes
        Properties fetchSize_500B = kafkaConsumerProperties();
        fetchSize_500B.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "max_fetch_size_500B");
        fetchSize_500B.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 500 + "");
        CompletableFuture.runAsync(
          new VariableFetchSizeKafkaListener("engine.sensors.temperature", fetchSize_500B)
        );

        // max.partition.fetch.bytes = 5.000 Bytes
        Properties fetchSize_5KB = kafkaConsumerProperties();
        fetchSize_5KB.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "max_fetch_size_5KB");
        fetchSize_5KB.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 5_000 + "");
        CompletableFuture.runAsync(
          new VariableFetchSizeKafkaListener("engine.sensors.temperature", fetchSize_5KB)
        );

        Thread.sleep(10_000L);
    }

    @Test
    void whenChangingMinFetchBytesProperty_thenAdjustWaitTimeWhilePolling() throws Exception {
        publishSensorData(300, "engine.sensors.temperature", 100L);

        // fetch.min.bytes = 1 byte (default)
        Properties minFetchSize_1B = kafkaConsumerProperties();
        minFetchSize_1B.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "min_fetch_size_1B");
        minFetchSize_1B.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1 + "");
        CompletableFuture.runAsync(
          new VariableFetchSizeKafkaListener("engine.sensors.temperature", minFetchSize_1B)
        );

        // fetch.min.bytes = 500 bytes
        Properties minFetchSize_500B = kafkaConsumerProperties();
        minFetchSize_500B.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "mim_fetch_size_500B");
        minFetchSize_500B.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 500 + "");
        CompletableFuture.runAsync(
          new VariableFetchSizeKafkaListener("engine.sensors.temperature", minFetchSize_500B)
        );

        Thread.sleep(10_000L);
    }


    private static Properties kafkaConsumerProperties() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }

    private void publishSensorData(int measurementsCount, String topic) {
        publishSensorData(measurementsCount, topic, 0L);
    }

    private void publishSensorData(int measurementsCount, String topic, long delayInMillis) {
        List<ProducerRecord<String, String>> records = IntStream.range(0, measurementsCount)
            .mapToObj(__ -> new ProducerRecord<>(topic, "key1", "temperature=255F"))
            .collect(Collectors.toList());

        CompletableFuture.runAsync(() -> {
            try (KafkaProducer<String, String> producer = testKafkaProducer()) {
                for (ProducerRecord<String, String> rec : records) {
                    producer.send(rec);
                    sleep(delayInMillis);
                }
            }
        });
    }

    private static KafkaProducer<String, String> testKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    private static void sleep(long delayInMillis) {
        try {
            Thread.sleep(delayInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}