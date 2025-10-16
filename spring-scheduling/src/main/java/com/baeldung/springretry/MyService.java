package com.baeldung.springretry;

import java.sql.SQLException;
import reactor.core.publisher.Mono;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;


public interface MyService {

    @Retryable
    void retryService();

    @Retryable(retryFor = SQLException.class)
    void retryServiceWithRecovery(String sql) throws SQLException;

    @Retryable(retryFor = SQLException.class , maxAttempts = 2, backoff = @Backoff(delay = 100))
    void retryServiceWithCustomization(String sql) throws SQLException;

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "${retry.maxAttempts}",
                        backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    void retryServiceWithExternalConfiguration(String sql) throws SQLException;

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 50))
    Mono<String> reactiveRetryService();

    @Recover
    void recover(SQLException e, String sql);

    // **NEW RECOVER METHOD FOR REACTIVE TYPE**
    @Recover
    Mono<String> recover(RuntimeException e);
    
    void templateRetryService();
}
