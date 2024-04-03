package com.baeldung.spring.cloud.aws.sqs.acknowledgement.model;

public enum OrderStatus {

    RECEIVED,

    PROCESSING,

    PROCESSED,

    ERROR,

    UNKNOWN

}
