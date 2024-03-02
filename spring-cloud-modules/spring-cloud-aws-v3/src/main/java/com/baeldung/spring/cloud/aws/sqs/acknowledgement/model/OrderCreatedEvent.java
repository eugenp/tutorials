package com.baeldung.spring.cloud.aws.sqs.acknowledgement.model;

import java.util.UUID;

public record OrderCreatedEvent(UUID id, UUID productId, int quantity) {

}
