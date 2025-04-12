package com.baeldung.monitoring;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
class KafkaConfig {

    @Bean
    public ConsumerFactory<String, String> myConsumerFactory(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
        cf.addListener(new MicrometerConsumerListener<>(meterRegistry,
            Collections.singletonList(new ImmutableTag("customTag", "customTagValue"))));
        return cf;
    }


    @Bean
    public ProducerFactory<String, ArticleCommentAddedEvent> producerFactory(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
        ProducerFactory pf = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        pf.addListener(new MicrometerProducerListener<String, String>(meterRegistry,
            Collections.singletonList(new ImmutableTag("customTag2", "customTagValue2"))));
        return pf;
    }

    @Bean
    public KafkaTemplate<String, ArticleCommentAddedEvent> kafkaTemplate(ProducerFactory<String, ArticleCommentAddedEvent> producerFactory) {
        var template = new KafkaTemplate<>(producerFactory);
        template.setMicrometerTags(Map.of(
            "clientId", "some-client-id",
            "topic", "baeldung.article-comment.added"
        ));

        template.setMicrometerTagsProvider(
            record -> Map.of("article-slug", record.key().toString()));
        return template;
    }

}