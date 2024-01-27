package com.baeldung.spring.kafka.trusted.packages;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.StringOrBytesSerializer;

import java.util.Map;

@Configuration
public class ProducerConfiguration {

    @Bean
    public KafkaTemplate<Object, SomeData> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<Object, SomeData> producerFactory() {
        JsonSerializer<SomeData> jsonSerializer = new JsonSerializer<>();
        jsonSerializer.setAddTypeInfo(true);
        return new DefaultKafkaProducerFactory<>(
          producerFactoryConfig(),
          new StringOrBytesSerializer(),
          jsonSerializer
        );
    }

    @Bean
    public Map<String, Object> producerFactoryConfig() {
        return Map.of(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://localhost:9092"
        );
    }
}