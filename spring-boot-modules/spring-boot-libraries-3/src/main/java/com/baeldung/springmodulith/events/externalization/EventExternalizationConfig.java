package com.baeldung.springmodulith.events.externalization;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

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
            it -> new ArticlePublishedKafkaEvent(it.slug(), it.title())
          )
          .build();
    }

    record ArticlePublishedKafkaEvent(String slug, String title) {
    }


    @Bean
    KafkaOperations<String, ArticlePublishedEvent> kafkaOperations(KafkaProperties kafkaProperties) {
        ProducerFactory<String, ArticlePublishedEvent> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        return new KafkaTemplate<>(producerFactory);
    }
}

