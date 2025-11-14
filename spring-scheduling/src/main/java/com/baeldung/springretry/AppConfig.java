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

        // FIX: SimpleRetryPolicy must be instantiated with a constructor
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3); // Set max attempts to 3 (Default is 3)

        retryTemplate.setRetryPolicy(retryPolicy);

        // Assuming DefaultListenerSupport is defined elsewhere
        // retryTemplate.registerListener(new DefaultListenerSupport());
        
        return retryTemplate;
    }

    // New bean to test no attempts functionality (fixed to maxAttempts = 1)
    @Bean
    public RetryTemplate retryTemplateNoAttempts() {
        RetryTemplate retryTemplate = new RetryTemplate();
        
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100l); // Shorter delay for quick test
        retryTemplate.setBackOffPolicy(fixedBackBackOffPolicy);
        
        // FIX: Using the constructor. Standard SimpleRetryPolicy requires maxAttempts >= 1.
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(1); // 1 attempt (i.e., no retry)
            
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }
}
