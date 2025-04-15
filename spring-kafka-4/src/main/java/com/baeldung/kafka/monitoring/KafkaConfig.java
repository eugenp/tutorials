package com.baeldung.kafka.monitoring;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
class KafkaConfig {

    @Bean
    ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
        cf.addListener(new MicrometerConsumerListener<>(meterRegistry, Collections.singletonList(new ImmutableTag("app-name", "article-comments-app"))));
        return cf;
    }

    @Bean
    ProducerFactory<String, ArticleCommentAddedEvent> producerFactory(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
        ProducerFactory pf = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        pf.addListener(
            new MicrometerProducerListener<String, String>(meterRegistry, Collections.singletonList(new ImmutableTag("app-name", "article-comments-app"))));
        return pf;
    }

    @Bean
    @Qualifier("articleCommentsKafkaTemplate")
    KafkaTemplate<String, ArticleCommentAddedEvent> articleCommentsKafkaTemplate(ProducerFactory<String, ArticleCommentAddedEvent> producerFactory) {
        var template = new KafkaTemplate<>(producerFactory);

        template.setMicrometerTags(Map.of("topic", "baeldung.article-comment.added"));
        template.setMicrometerTagsProvider(record -> Map.of("article-slug", record.key()
            .toString()));

        return template;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> customKafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        ContainerProperties containerProps = factory.getContainerProperties();
        containerProps.setMicrometerTags(Map.of("app-name", "article-comments-app"));
        containerProps.setMicrometerTagsProvider(record -> Map.of("article-slug", record.key()
            .toString()));
        return factory;
    }

}