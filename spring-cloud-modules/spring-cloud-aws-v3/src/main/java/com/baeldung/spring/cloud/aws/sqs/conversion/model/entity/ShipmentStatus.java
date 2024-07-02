package com.baeldung.spring.cloud.aws.sqs.conversion.model.entity;

public enum ShipmentStatus {
    REQUESTED,
    PROCESSED,
    CUSTOMS_CHECK,
    READY_FOR_DISPATCH,
    SENT,
    DELIVERED
}