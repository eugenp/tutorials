package com.baeldung.kafka.synchronous;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "com.baeldung.kafka.synchronous")
record SynchronousKafkaProperties(
    @NotBlank
    String requestTopic,

    @NotBlank
    String replyTopic,

    @NotNull @DurationMin(seconds = 10) @DurationMax(minutes = 2)
    Duration replyTimeout
) {
}