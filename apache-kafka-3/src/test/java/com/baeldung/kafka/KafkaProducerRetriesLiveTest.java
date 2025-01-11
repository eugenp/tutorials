package com.baeldung.kafka;

import static java.util.Collections.singleton;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRY_BACKOFF_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class KafkaProducerRetriesLiveTest {

    static AdminClient adminClient;

    @Container
    static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void beforeAll() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_CONTAINER.getBootstrapServers());
        adminClient = AdminClient.create(props);
    }

    @Test
    void givenDefaultConfig_whenMessageCannotBeSent_thenKafkaProducerRetries() throws Exception {
        NewTopic newTopic = new NewTopic("test-topic-1", 1, (short) 1)
            .configs(mapOf("min.insync.replicas", "2"));
        adminClient.createTopics(singleton(newTopic)).all().get();

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic-1", "test-value");
        assertThatThrownBy(() -> producer.send(record)
            .get()).isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(org.apache.kafka.common.errors.TimeoutException.class)
            .hasMessageContaining("Expiring 1 record(s) for test-topic-1-0");
    }

    @Test
    void givenCustomConfig_whenMessageCannotBeSent_thenKafkaProducerRetries() throws Exception {
        NewTopic newTopic = new NewTopic("test-topic-2", 1, (short) 1)
            .configs(mapOf("min.insync.replicas", "2"));
        adminClient.createTopics(singleton(newTopic)).all().get();

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(REQUEST_TIMEOUT_MS_CONFIG, "5000");
        props.put(RETRIES_CONFIG, 20);
        props.put(RETRY_BACKOFF_MS_CONFIG, "500");
        props.put(DELIVERY_TIMEOUT_MS_CONFIG, "5000");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic-2", "test-value");
        assertThatThrownBy(() -> producer.send(record).get())
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(org.apache.kafka.common.errors.TimeoutException.class)
            .hasMessageContaining("Expiring 1 record(s) for test-topic-2-0");
    }

    static Map<String, String> mapOf(String key, String value) {
        return new HashMap<String, String>() {{
            put(key, value);
        }};
    }
}
