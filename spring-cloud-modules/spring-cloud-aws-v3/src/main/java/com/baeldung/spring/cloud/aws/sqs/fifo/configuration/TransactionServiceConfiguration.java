package com.baeldung.spring.cloud.aws.sqs.fifo.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({ TransactionEventsQueuesProperties.class })
@Configuration
public class TransactionServiceConfiguration {
}
