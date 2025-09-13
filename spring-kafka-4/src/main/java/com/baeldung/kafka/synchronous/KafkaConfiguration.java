package com.baeldung.kafka.synchronous;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(SynchronousKafkaProperties.class)
class KafkaConfiguration {

    private final SynchronousKafkaProperties synchronousKafkaProperties;

    KafkaConfiguration(SynchronousKafkaProperties synchronousKafkaProperties) {
        this.synchronousKafkaProperties = synchronousKafkaProperties;
    }

    @Bean
    KafkaMessageListenerContainer<String, NotificationDispatchResponse> kafkaMessageListenerContainer(
        ConsumerFactory<String, NotificationDispatchResponse> consumerFactory
    ) {
        String replyTopic = synchronousKafkaProperties.replyTopic();
        ContainerProperties containerProperties = new ContainerProperties(replyTopic);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    @Bean
    ReplyingKafkaTemplate<String, NotificationDispatchRequest, NotificationDispatchResponse> replyingKafkaTemplate(
        ProducerFactory<String, NotificationDispatchRequest> producerFactory,
        KafkaMessageListenerContainer<String, NotificationDispatchResponse> kafkaMessageListenerContainer
    ) {
        Duration replyTimeout = synchronousKafkaProperties.replyTimeout();
        var replyingKafkaTemplate = new ReplyingKafkaTemplate<>(producerFactory, kafkaMessageListenerContainer);
        replyingKafkaTemplate.setDefaultReplyTimeout(replyTimeout);
        return replyingKafkaTemplate;
    }

    @Bean
    KafkaTemplate<String, NotificationDispatchResponse> kafkaTemplate(ProducerFactory<String, NotificationDispatchResponse> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NotificationDispatchRequest>> kafkaListenerContainerFactory(
        ConsumerFactory<String, NotificationDispatchRequest> consumerFactory,
        KafkaTemplate<String, NotificationDispatchResponse> kafkaTemplate
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, NotificationDispatchRequest>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

}