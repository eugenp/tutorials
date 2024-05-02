package com.baeldung.spring.cloud.aws.sqs.conversion.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.DomesticShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.InternationalShipmentRequestedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import io.awspring.cloud.sqs.MessageHeaderUtils;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@EnableConfigurationProperties({ ShipmentEventsQueuesProperties.class, ShippingHeaderTypesProperties.class })
@Configuration
public class ShipmentServiceConfiguration {

    private final ShippingHeaderTypesProperties typesProperties;

    private final SqsAsyncClient sqsAsyncClient;

    public ShipmentServiceConfiguration(ShippingHeaderTypesProperties typesProperties, SqsAsyncClient sqsAsyncClient) {
        this.typesProperties = typesProperties;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        // Custom deserializer for LocalDate
        LocalDateDeserializer deser = new LocalDateDeserializer(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US));
        module.addDeserializer(LocalDate.class, deser);
        mapper.registerModule(module);
        return mapper;
    }

    @Bean
    public SqsMessageListenerContainerFactory<?> defaultSqsListenerContainerFactory(ObjectMapper objectMapper) {
        SqsMessagingMessageConverter converter = new SqsMessagingMessageConverter();
        converter.setPayloadTypeMapper(message -> message.getHeaders()
            .containsKey(typesProperties.getHeaderName()) ? switch (MessageHeaderUtils.getHeaderAsString(message, typesProperties.getHeaderName())) {
            // We need to use Strings known at compile time for the switch expressions
            case "INTERNATIONAL" -> InternationalShipmentRequestedEvent.class;
            case "DOMESTIC" -> DomesticShipmentRequestedEvent.class;
            default -> throw new RuntimeException("Invalid shipping type");
        } : Object.class); converter.setObjectMapper(objectMapper);

        return SqsMessageListenerContainerFactory.builder()
            .sqsAsyncClient(sqsAsyncClient)
            .configure(configure -> configure.messageConverter(converter))
            .build();
    }
}
