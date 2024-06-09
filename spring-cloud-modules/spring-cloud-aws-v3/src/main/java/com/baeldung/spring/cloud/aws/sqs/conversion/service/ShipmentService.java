package com.baeldung.spring.cloud.aws.sqs.conversion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.DomesticShipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.InternationalShipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.Shipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.ShipmentStatus;

@Service
public class ShipmentService {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);

    private final Map<UUID, Shipment> shippingRepository = new ConcurrentHashMap<>();

    public void processShippingRequest(Shipment shipment) {
        logger.info("Processing shipping for order: {}", shipment.getOrderId());
        shipment.setStatus(ShipmentStatus.PROCESSED);
        shippingRepository.put(shipment.getOrderId(), shipment);
        logger.info("Shipping request processed: {}", shipment.getOrderId());
    }

    public Shipment getShipment(UUID requestId) {
        return shippingRepository.get(requestId);
    }

    public void processDomesticShipping(DomesticShipment shipment) {
        logger.info("Processing domestic shipping for order: {}", shipment.getOrderId());
        shipment.setStatus(ShipmentStatus.READY_FOR_DISPATCH);
        shippingRepository.put(shipment.getOrderId(), shipment);
        logger.info("Domestic shipping processed: {}", shipment.getOrderId());
    }

    public void processInternationalShipping(InternationalShipment shipment) {
        logger.info("Processing international shipping for order: {}", shipment.getOrderId());
        shipment.setStatus(ShipmentStatus.CUSTOMS_CHECK);
        shippingRepository.put(shipment.getOrderId(), shipment);
        logger.info("International shipping processed: {}", shipment.getOrderId());
    }
}
