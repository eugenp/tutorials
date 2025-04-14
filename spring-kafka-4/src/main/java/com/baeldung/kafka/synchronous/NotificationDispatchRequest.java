package com.baeldung.kafka.synchronous;

record NotificationDispatchRequest(String emailId, String content) {
}