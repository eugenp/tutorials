package com.baeldung.spring.cloud.aws.sqs.conversion.listener;

import org.springframework.stereotype.Component;

import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.DomesticShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.InternationalShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.event.ShipmentRequestedEvent;
import com.baeldung.spring.cloud.aws.sqs.conversion.service.ShipmentService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class ShipmentRequestListener {

    private final ShipmentService shippingService;

    public ShipmentRequestListener(ShipmentService shippingService) {
        this.shippingService = shippingService;
    }

    @SqsListener("${events.queues.shipping.simple-pojo-conversion-queue}")
    public void receiveShipmentRequest(ShipmentRequestedEvent shipmentRequestedEvent) {
        shippingService.processShippingRequest(shipmentRequestedEvent.toDomain());
    }

    @SqsListener("${events.queues.shipping.custom-object-mapper-queue}")
    public void receiveShipmentRequestWithCustomObjectMapper(ShipmentRequestedEvent shipmentRequestedEvent) {
        shippingService.processShippingRequest(shipmentRequestedEvent.toDomain());
    }

    @SqsListener(queueNames = "${events.queues.shipping.subclass-deserialization-queue}")
    public void receiveShippingRequestWithType(ShipmentRequestedEvent shipmentRequestedEvent) {
        if (shipmentRequestedEvent instanceof InternationalShipmentRequestedEvent event) {
            shippingService.processInternationalShipping(event.toDomain());
        } else if (shipmentRequestedEvent instanceof DomesticShipmentRequestedEvent event) {
            shippingService.processDomesticShipping(event.toDomain());
        } else {
            throw new RuntimeException("Event type not supported " + shipmentRequestedEvent.getClass()
                .getSimpleName());
        }
    }

}
