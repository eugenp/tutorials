package com.baeldung.spring.cloud.aws.sqs.conversion.model.event;

import java.time.LocalDate;
import java.util.UUID;

import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.DomesticShipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.ShipmentStatus;

public class DomesticShipmentRequestedEvent extends ShipmentRequestedEvent {

    private String deliveryRouteCode;

    public DomesticShipmentRequestedEvent(){}

    public DomesticShipmentRequestedEvent(UUID orderId, String customerAddress, LocalDate shipBy, String deliveryRouteCode) {
        super(orderId, customerAddress, shipBy);
        this.deliveryRouteCode = deliveryRouteCode;
    }

    public DomesticShipment toDomain() {
        return new DomesticShipment(getOrderId(), getCustomerAddress(), getShipBy(), ShipmentStatus.REQUESTED, deliveryRouteCode);
    }

    public String getDeliveryRouteCode() {
        return deliveryRouteCode;
    }

    public void setDeliveryRouteCode(String deliveryRouteCode) {
        this.deliveryRouteCode = deliveryRouteCode;
    }

}
