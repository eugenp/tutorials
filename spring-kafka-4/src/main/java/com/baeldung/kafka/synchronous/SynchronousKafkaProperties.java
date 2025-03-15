package com.baeldung.kafka.synchronous;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "com.baeldung.kafka.synchronous")
record SynchronousKafkaProperties(String requestTopic, String replyTopic, Duration replyTimeout) {
}