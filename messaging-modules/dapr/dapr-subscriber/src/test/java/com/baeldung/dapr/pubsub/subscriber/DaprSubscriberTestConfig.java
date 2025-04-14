package com.baeldung.dapr.pubsub.subscriber;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.dapr.client.DaprClient;
import io.dapr.spring.boot.autoconfigure.pubsub.DaprPubSubProperties;
import io.dapr.spring.messaging.DaprMessagingTemplate;

@Configuration
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprSubscriberTestConfig {

    @Bean
    public DaprMessagingTemplate<RideRequest> messagingTemplate(DaprClient daprClient,
            DaprPubSubProperties daprPubSubProperties) {
        return new DaprMessagingTemplate<>(daprClient, daprPubSubProperties.getName(), false);
    }
}
