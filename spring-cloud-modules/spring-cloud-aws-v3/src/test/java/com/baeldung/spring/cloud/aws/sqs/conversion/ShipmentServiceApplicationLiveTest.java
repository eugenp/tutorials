package com.baeldung.spring.cloud.aws.sqs.conversion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.spring.cloud.aws.sqs.BaseSqsLiveTest;
import com.baeldung.spring.cloud.aws.sqs.conversion.configuration.ShipmentEventsQueuesProperties;
import com.baeldung.spring.cloud.aws.sqs.conversion.configuration.ShippingHeaderTypesProperties;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.DomesticShipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.InternationalShipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.DomesticShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.InternationalShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.ShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.ShipmentStatus;
import com.baeldung.spring.cloud.aws.sqs.conversion.service.ShipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@ActiveProfiles("shipping")
@SpringBootTest
public class ShipmentServiceApplicationLiveTest extends BaseSqsLiveTest {

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentEventsQueuesProperties queuesProperties;

    @Autowired
    private ShippingHeaderTypesProperties headerTypesProperties;

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenPojoPayload_whenMessageReceived_shouldDeserializeCorrectly() {
        var orderId = UUID.randomUUID();
        var shipmentRequestedEvent = new ShipmentRequestedEvent(orderId, "123 Main St", LocalDate.now()
            .plusDays(5));

        sqsTemplate.send(queuesProperties.getSimplePojoConversionQueue(), shipmentRequestedEvent);

        await().atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                assertThat(shipmentService.getShipment(orderId)).isNotNull();
                assertThat(shipmentService.getShipment(orderId)).usingRecursiveComparison()
                    .ignoringFields("status")
                    .isEqualTo(shipmentRequestedEvent);
                assertThat(shipmentService.getShipment(orderId)
                    .getStatus()).isEqualTo(ShipmentStatus.PROCESSED);
            });
    }

    @Test
    void givenShipmentRequestWithCustomDateFormat_whenMessageReceived_shouldDeserializeDateCorrectly() {
        var orderId = UUID.randomUUID();
        var shipBy = LocalDate.now()
            .plusDays(5)
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        var jsonMessage = """
            {
                "orderId": "%s",
                "customerAddress": "123 Main St",
                "shipBy": "%s"
            }
            """.formatted(orderId, shipBy);

        sendRawMessage(queuesProperties.getCustomObjectMapperQueue(), jsonMessage);

        await().atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                var shipment = shipmentService.getShipment(orderId);
                assertThat(shipment).isNotNull();
                assertThat(shipment.getShipBy()).isEqualTo(LocalDate.parse(shipBy, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            });
    }

    private void sendRawMessage(String queueName, String jsonMessage) {
        sqsAsyncClient.getQueueUrl(req -> req.queueName(queueName))
            .thenCompose(resp -> sqsAsyncClient.sendMessage(req -> req.messageBody(jsonMessage)
                .queueUrl(resp.queueUrl())))
            .join();
    }

    @Test
    void givenPayloadWithSubclasses_whenMessageReceived_shouldDeserializeCorrectType() {
        var domesticOrderId = UUID.randomUUID();
        var domesticEvent = new DomesticShipmentRequestedEvent(domesticOrderId, "123 Main St", LocalDate.now()
            .plusDays(5), "XPTO1234");

        var internationalOrderId = UUID.randomUUID();
        InternationalShipmentRequestedEvent internationalEvent = new InternationalShipmentRequestedEvent(internationalOrderId, "123 Main St", LocalDate.now()
            .plusDays(15), "Canada", "HS Code: 8471.30, Origin: China, Value: $500");

        var customTemplate = SqsTemplate.builder()
            .sqsAsyncClient(sqsAsyncClient)
            .configureDefaultConverter(converter -> {
                converter.doNotSendPayloadTypeHeader();
                converter.setObjectMapper(objectMapper);
            })
            .build();

        customTemplate.send(to -> to.queue(queuesProperties.getDeserializesSubclass())
            .payload(internationalEvent)
            .header(headerTypesProperties.getHeaderName(), headerTypesProperties.getInternational()));

        customTemplate.send(to -> to.queue(queuesProperties.getDeserializesSubclass())
            .payload(domesticEvent)
            .header(headerTypesProperties.getHeaderName(), headerTypesProperties.getDomestic()));

        await().atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                var domesticShipment = (DomesticShipment) shipmentService.getShipment(domesticOrderId);
                assertThat(domesticShipment).isNotNull();
                assertThat(domesticShipment.getStatus()).isEqualTo(ShipmentStatus.READY_FOR_DISPATCH);

                var internationalShipment = (InternationalShipment) shipmentService.getShipment(internationalOrderId);
                assertThat(internationalShipment).isNotNull();
                assertThat(internationalShipment.getStatus()).isEqualTo(ShipmentStatus.CUSTOMS_CHECK);
            });
    }

}
