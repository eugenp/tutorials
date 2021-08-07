package com.baeldung.monitoring.simulation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${monitor.kafka.bootstrap.config}")
    private String bootstrapAddress;
    @Value(value = "${monitor.kafka.consumer.groupid}")
    private String groupId;
    @Value(value = "${monitor.kafka.consumer.groupid.simulate}")
    private String simulateGroupId;
    @Value(value = "${monitor.producer.simulate}")
    private boolean enabled;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        if (enabled) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        } else {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, simulateGroupId);
        }
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 0);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        if (enabled) {
            factory.setConsumerFactory(consumerFactory(groupId));
        } else {
            factory.setConsumerFactory(consumerFactory(simulateGroupId));
        }
        return factory;
    }
}
