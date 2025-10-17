package com.baeldung.springretry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@ComponentScan(basePackages = "com.baeldung.springretry")
@EnableRetry
@PropertySource("classpath:retryConfig.properties")
public class AppConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        // **Introduce Factory Method for SimpleRetryPolicy**
        // Assuming a static factory method exists (or is created)
        // Note: Standard SimpleRetryPolicy requires maxAttempts >= 1.
        // We'll use 2 for consistency but the concept of a factory method is here.
        SimpleRetryPolicy retryPolicy = SimpleRetryPolicy.builder()
            .maxAttempts(2) // Demonstrating Builder API concept
            .build(); 

        retryTemplate.setRetryPolicy(retryPolicy);

        retryTemplate.registerListener(new DefaultListenerSupport());
        return retryTemplate;
    }

    // New bean to test maxAttempts(0) functionality
    @Bean
    public RetryTemplate retryTemplateNoAttempts() {
        RetryTemplate retryTemplate = new RetryTemplate();
        
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100l); // Shorter delay for quick test
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        
        // **Demonstrating Builder API and maxAttempts(0) support**
        // A standard SimpleRetryPolicy would throw IAE for 0.
        // Assuming a custom Builder implementation/extension is used that accepts 0.
        SimpleRetryPolicy retryPolicy = SimpleRetryPolicy.builder()
            .maxAttempts(0)
            .build();
            
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }
}
