package com.baeldung.dapr.pubsub.publisher;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dapr.pubsub.model.RideRequest;

import io.dapr.client.DaprClient;
import io.dapr.spring.boot.autoconfigure.pubsub.DaprPubSubProperties;
import io.dapr.spring.messaging.DaprMessagingTemplate;

@Configuration
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprMessagingConfig {

    @Bean
    public DaprMessagingTemplate<RideRequest> messagingTemplate(DaprClient client, DaprPubSubProperties config) {
        return new DaprMessagingTemplate<>(client, config.getName(), false);
    }
}
