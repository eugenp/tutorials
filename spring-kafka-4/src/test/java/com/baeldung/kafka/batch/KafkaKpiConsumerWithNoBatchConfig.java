package com.baeldung.kafka.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@TestConfiguration
@Profile("no-batch")
public class KafkaKpiConsumerWithNoBatchConfig {

    private final Logger logger = LoggerFactory.getLogger(KafkaKpiConsumerWithNoBatchConfig.class);

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaKpiListenerContainerFactory(
        ConsumerFactory<String, String> consumerFactory, EmbeddedKafkaBroker embeddedKafka) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}
