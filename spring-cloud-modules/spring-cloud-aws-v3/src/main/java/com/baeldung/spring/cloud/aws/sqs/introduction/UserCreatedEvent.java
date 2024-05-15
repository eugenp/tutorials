package com.baeldung.spring.cloud.aws.sqs.introduction;

public record UserCreatedEvent(String id, String username, String email) {

}
