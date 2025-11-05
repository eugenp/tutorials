package com.baeldung.spring.cloud.aws.sqs.conversion.model.entity;

import java.time.LocalDate;
import java.util.UUID;

public class InternationalShipment extends Shipment {

    private String destinationCountry;
    private String customsInfo;

    public InternationalShipment(UUID orderId, String customerAddress, LocalDate shipBy, ShipmentStatus status,
        String destinationCountry, String customsInfo) {
        super(orderId, customerAddress, shipBy, status);
        this.destinationCountry = destinationCountry;
        this.customsInfo = customsInfo;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getCustomsInfo() {
        return customsInfo;
    }

    public void setCustomsInfo(String customsInfo) {
        this.customsInfo = customsInfo;
    }
}
