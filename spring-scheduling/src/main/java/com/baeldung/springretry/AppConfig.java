package com.baeldung.springretry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
// Assuming org.springframework.resilience.annotation.EnableResilientMethods is available in Spring 7
import org.springframework.resilience.annotation.EnableResilientMethods;

@Configuration
@ComponentScan(basePackages = "com.baeldung.springretry")
@EnableRetry
@EnableResilientMethods    
@PropertySource("classpath:retryConfig.properties")
public class AppConfig {

    /**
     * Configures a RetryTemplate with a FixedBackOffPolicy (2s delay) and
     * a SimpleRetryPolicy (3 max attempts).
     * @return The configured RetryTemplate bean.
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        // Set fixed delay between retries to 2000ms
        fixedBackOffPolicy.setBackOffPeriod(2000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        // Set max attempts to 3 (Default for SimpleRetryPolicy is 3 if using the map-based constructor, 
        // but the simple constructor ensures max attempts is 3)
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3); 
        retryTemplate.setRetryPolicy(retryPolicy);
            
        return retryTemplate;
    }

    /**
     * Configures a RetryTemplate that allows only 1 attempt (i.e., no retry).
     * Used for testing scenarios where no retry behavior is expected.
     * @return The configured RetryTemplate bean for no-retry scenarios.
     */
    @Bean
    public RetryTemplate retryTemplateNoAttempts() {
        RetryTemplate retryTemplate = new RetryTemplate();
            
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100l); // Shorter delay for quick test
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
            
        // 1 attempt means the initial call and no further retries.
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(1); 
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
