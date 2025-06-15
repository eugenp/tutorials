package com.baeldung.avro.deserialization.exception;

import static java.time.Duration.ofSeconds;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.avro.deserialization.exception.avro.Article;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@SpringBootTest
@ActiveProfiles("avro-magic-byte")
class AvroMagicByteLiveTest {

    @Container
    @ServiceConnection
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:4.0.0"));

    @Autowired
    private AvroMagicByteApp listener;

    @Test
    void whenSendingCorrectArticle_thenItsAddedToTheBlog() throws Exception {
        avroKafkaTemplate().send("baeldung.article.published", aTestArticle("Avro Magic Byte"))
            .get();

        await().untilAsserted(() -> assertThat(listener.blog).containsExactly("Avro Magic Byte"));
    }

    @Test
    void whenSendingMalformedMessage_thenSendToDLQ() throws Exception {
        stringKafkaTemplate().send("baeldung.article.published", "not a valid avro message!")
            .get();

        var dlqRecord = listenForOneMessage("baeldung.article.published-dlt", ofSeconds(5L));

        assertThat(dlqRecord.value()).isEqualTo("not a valid avro message!");
    }

    private static KafkaTemplate<Object, Object> avroKafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }

    private static KafkaTemplate<Object, Object> stringKafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory(), Map.of(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()));
    }

    private static DefaultKafkaProducerFactory<Object, Object> kafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
            Map.of(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(), KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName(), "schema.registry.url", "mock://test"));
    }

    private static ConsumerRecord<?, ?> listenForOneMessage(String topic, Duration timeout) {
        return KafkaTestUtils.getOneRecord(kafka.getBootstrapServers(), "test-group-id", topic, 0, false, true, timeout);
    }

    private static Article aTestArticle(String title) {
        return new Article(title, "John Doe", List.of("avro", "kafka", "spring"));
    }

}
