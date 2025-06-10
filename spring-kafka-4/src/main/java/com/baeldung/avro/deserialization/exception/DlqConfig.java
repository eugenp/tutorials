package com.baeldung.avro.deserialization.exception;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
class DlqConfig {

    @Bean
    DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer dlqPublishingRecoverer) {
        return new DefaultErrorHandler(dlqPublishingRecoverer);
    }

    @Bean
    DeadLetterPublishingRecoverer dlqPublishingRecoverer(KafkaTemplate<byte[], byte[]> bytesKafkaTemplate) {
        return new DeadLetterPublishingRecoverer(bytesKafkaTemplate);
    }

    @Bean("bytesKafkaTemplate")
    KafkaTemplate<?, ?> bytesTemplate(KafkaProperties kafkaProperties) {
        var kafkaProducerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        return new KafkaTemplate<>(kafkaProducerFactory, Map.of(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName()));
    }

}
