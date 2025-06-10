package com.baeldung.avro.deserialization.exception;

import static java.time.Duration.ofSeconds;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.avro.deserialization.exception.avro.Article;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("avro-magic-byte")
public class AvroMagicByteLiveTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer();

    @DynamicPropertySource
    static void setKafkaProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private AvroMagicByteApp listener;

    @Test
    void whenSendingCorrectArticle_thenItsAddedToTheBlog() throws Exception {
        Article article = new Article("Avro Magic Byte", "John Doe", List.of("avro", "kafka", "spring"));

        avroKafkaTemplate().send("baeldung.article.published", article)
            .get();

        await().untilAsserted(() -> assertThat(listener.blog).containsExactly("Avro Magic Byte"));
    }

    @Test
    void whenSendingMalformedMessage_thenSendToDLQ() throws Exception {
        stringKafkaTemplate()
            .send("baeldung.article.published", "not a valid avro message!")
            .get();

        var dlqRecord = KafkaTestUtils.getOneRecord(
            kafka.getBootstrapServers(),
            "test-group-id",
            "baeldung.article.published-dlt", 0, false, true,
            ofSeconds(5L)
        );

        assertThat(dlqRecord)
            .isNotNull()
            .extracting(record -> record.value().toString())
            .isEqualTo(MALFORMED_AVRO_MESSAGE);
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

    public static final String MALFORMED_AVRO_MESSAGE = "not a valid avro message!";
}
