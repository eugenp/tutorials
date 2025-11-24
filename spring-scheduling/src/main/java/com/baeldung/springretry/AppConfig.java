package com.baeldung.springretry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
//import org.springframework.resilience.annotation.EnableResilientMethods;

@Configuration
@ComponentScan(basePackages = "com.baeldung.springretry")
@EnableRetry
//@EnableResilientMethods 
@PropertySource("classpath:retryConfig.properties")
public class AppConfig {

    // Helper method for the FixedBackOffPolicy
    private FixedBackOffPolicy fixedBackOffPolicy(long backOffPeriod) {
        FixedBackOffPolicy policy = new FixedBackOffPolicy();
        policy.setBackOffPeriod(backOffPeriod);
        return policy;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        // Use RetryTemplate.builder() which supports a maxAttempts() method
        return RetryTemplate.builder() 
            .maxAttempts(3) // Directly set max attempts here
            .customBackoff(fixedBackOffPolicy(2000L))
            .withListener(new DefaultListenerSupport())
            .build();
    }

    @Bean
    public RetryTemplate retryTemplateNoRetry() {
        // Use RetryTemplate.builder()
        return RetryTemplate.builder() 
            .maxAttempts(1) // Directly set max attempts (for demonstration)
            .customBackoff(fixedBackOffPolicy(100L))
            .build();
    }
}
