package com.baeldung.spring.modulith.externalization;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

import java.util.Objects;

@Configuration
class EventExternalizationConfig {

    @Bean
    EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration.externalizing()
          .select(EventExternalizationConfiguration.annotatedAsExternalized())
          .route(
            ArticlePublishedEvent.class,
            it -> RoutingTarget.forTarget("baeldung.articles.published").andKey(it.slug())
          )
          .mapping(
            ArticlePublishedEvent.class,
            it -> new PostPublishedKafkaEvent(it.slug(), it.title())
          )
          .route(
            WeeklySummaryPublishedEvent.class,
            it -> RoutingTarget.forTarget("baeldung.articles.published").andKey(it.handle())
          )
          .mapping(
            WeeklySummaryPublishedEvent.class,
            it -> new PostPublishedKafkaEvent(it.handle(), it.heading())
          )
          .build();
    }

    @Bean
    @Primary
    @Profile("modulith")
    KafkaOperations<String, ArticlePublishedEvent> kafkaOperations(KafkaProperties kafkaProperties) {
        ProducerFactory<String, ArticlePublishedEvent> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        return new KafkaTemplate<>(producerFactory);
    }

    record PostPublishedKafkaEvent(String slug, String title) {
        PostPublishedKafkaEvent {
            Objects.requireNonNull(slug, "Article Slug must not be null!");
        }
    }

}

