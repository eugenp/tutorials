package com.baeldung.retryable.transactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry(order = Ordered.LOWEST_PRECEDENCE)
@SpringBootApplication
public class RetryableTransactionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetryableTransactionalApplication.class, args);
    }
}
