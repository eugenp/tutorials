package com.baeldung.kafka.batch;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@TestConfiguration
@Profile("batch")
public class KafkaKpiConsumerWithBatchConfig {
    @Bean(name="kafkaKpiListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaKpiBatchListenerContainerFactory(
        ConsumerFactory<String, String> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory();

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "20");
        consumerFactory.updateConfigs(configProps);
        factory.setConcurrency(1);
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setBatchListener(true);

        return factory;
    }
}
