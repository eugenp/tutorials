package com.baeldung.dsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class JavaDSLExamplesConfig {

    @Bean
    public IntegrationFlow upcase() {
        return f -> IntegrationFlows.from("input")
            .<String, String> transform(String::toUpperCase)
            .get();
    }

    // Channels
    @Bean
    public MessageChannel publishSubscribe() {
        return MessageChannels.publishSubscribe()
            .get();
    }

    @Bean
    public IntegrationFlow channelFlow() {
        return IntegrationFlows.from("input")
            .channel(publishSubscribe())
            .get();
    }

    // Bridge
    @Bean
    public IntegrationFlow bridgeFlow() {
        return IntegrationFlows.from("pollable")
            .bridge()
            .channel("subscribable")
            .get();
    }

    // Service Activator
    @Bean
    @ServiceActivator
    public MessageHandler messageHandler() {
        return message -> System.out.println(message.getPayload());
    }

    @Bean
    IntegrationFlow handlerImplFLow() {
        return IntegrationFlows.from("input")
            .handle(messageHandler())
            .get();
    }

    @Bean
    IntegrationFlow handlerLambdaFLow() {
        return IntegrationFlows.from("input")
            .<String> handle((payload, headers) -> payload.toUpperCase())
            .get();
    }

    // Inbound Channel Adapter
    @Bean
    @InboundChannelAdapter(value = "inputChannel")
    public MessageSource<String> messageSource() {
        return () -> new GenericMessage<>("payload");
    }

    IntegrationFlow inboundChannelAdapterFlow() {
        return IntegrationFlows.from(messageSource())
            .get();
    }
}
