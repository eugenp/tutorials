package com.baeldung.asyncwithretry;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableRetry
@ComponentScan("com.baeldung.asyncwithretry")
public class AsyncConfig {
}