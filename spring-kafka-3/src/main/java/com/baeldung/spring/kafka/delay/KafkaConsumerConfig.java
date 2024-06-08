package com.baeldung.spring.kafka.delay;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerPartitionPausingBackOffManager;
import org.springframework.kafka.listener.ContainerPausingBackOffHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaConsumerBackoffManager;
import org.springframework.kafka.listener.ListenerContainerPauseService;
import org.springframework.kafka.listener.ListenerContainerRegistry;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory,
        ListenerContainerRegistry registry, TaskScheduler scheduler) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        KafkaConsumerBackoffManager backOffManager = createBackOffManager(registry, scheduler);
        factory.getContainerProperties()
            .setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setContainerCustomizer(container -> {
            DelayedMessageListenerAdapter<Object, Object> delayedAdapter = wrapWithDelayedMessageListenerAdapter(backOffManager, container);
            delayedAdapter.setDelayForTopic("web.orders", Duration.ofSeconds(10));
            delayedAdapter.setDefaultDelay(Duration.ZERO);
            container.setupMessageListener(delayedAdapter);
        });
        return factory;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @SuppressWarnings("unchecked")
    private DelayedMessageListenerAdapter<Object, Object> wrapWithDelayedMessageListenerAdapter(KafkaConsumerBackoffManager backOffManager,
        ConcurrentMessageListenerContainer<Object, Object> container) {
        return new DelayedMessageListenerAdapter<>((MessageListener<Object, Object>) container.getContainerProperties()
            .getMessageListener(), backOffManager, container.getListenerId());
    }

    private ContainerPartitionPausingBackOffManager createBackOffManager(ListenerContainerRegistry registry, TaskScheduler scheduler) {
        return new ContainerPartitionPausingBackOffManager(registry,
            new ContainerPausingBackOffHandler(new ListenerContainerPauseService(registry, scheduler)));
    }

}
