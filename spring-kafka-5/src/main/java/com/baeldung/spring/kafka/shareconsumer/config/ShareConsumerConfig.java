package com.baeldung.spring.kafka.shareconsumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultShareConsumerFactory;
import org.springframework.kafka.core.ShareConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import com.baeldung.spring.kafka.shareconsumer.model.Event;

@EnableKafka
@Configuration
public class ShareConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${share.consumer.concurrency:5}")
    private int concurrency;

    @Bean
    public ShareConsumerFactory<String, Event> shareConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.baeldung.spring.kafka.shareconsumer.model");
        return new DefaultShareConsumerFactory<>(props);
    }

    @Bean
    public ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory(ShareConsumerFactory<String, Event> shareConsumerFactory) {
        ShareKafkaListenerContainerFactory<String, Event> factory = new ShareKafkaListenerContainerFactory<>(shareConsumerFactory);
        factory.getContainerProperties()
            .setShareAckMode(ContainerProperties.ShareAckMode.IMPLICIT);
        factory.setConcurrency(concurrency);
        return factory;
    }

}
