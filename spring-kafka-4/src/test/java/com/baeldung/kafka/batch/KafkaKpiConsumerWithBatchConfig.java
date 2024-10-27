package com.baeldung.kafka.batch;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@TestConfiguration
@Profile("batch")
public class KafkaKpiConsumerWithBatchConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaKpiListenerContainerFactory(
        ConsumerFactory<String, String> consumerFactory, EmbeddedKafkaBroker embeddedKafka) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory();
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "20");
//        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerFactory.updateConfigs(configProps);

        factory.setConsumerFactory(consumerFactory);
        //factory.setConcurrency(3);

        factory.getContainerProperties().setPollTimeout(3000);
        factory.setBatchListener(true);
        return factory;
    }
}
