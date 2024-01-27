package com.baeldung.spring.kafka.trusted.packages;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class ListenerConfiguration {

    @Bean("messageListenerContainer")
    public ConcurrentKafkaListenerContainerFactory<String, SomeData> messageListenerContainer() {
        ConcurrentKafkaListenerContainerFactory<String, SomeData> container = new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(someDataConsumerFactory());
        return container;
    }

    @Bean
    public ConsumerFactory<String, SomeData> someDataConsumerFactory() {
        JsonDeserializer<SomeData> payloadJsonDeserializer = new JsonDeserializer<>();
        payloadJsonDeserializer.trustedPackages("com.baeldung.spring.kafka");
        return new DefaultKafkaConsumerFactory<>(
          consumerConfigs(),
          new StringDeserializer(),
          payloadJsonDeserializer
        );
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return Map.of(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://localhost:9092",
          ConsumerConfig.GROUP_ID_CONFIG, "some-group-id"
        );
    }
}