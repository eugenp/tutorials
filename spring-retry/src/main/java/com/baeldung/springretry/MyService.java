package com.baeldung.springretry;

import java.sql.SQLException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface MyService {

    @Retryable
    void defaultRetryService();

    @Retryable(value = { SQLException.class }, maxAttempts = 2, backoff = @Backoff(delay = 5000))
    void customRetryService(String sql) throws SQLException;

    @Recover
    void recover(SQLException e, String sql);

    void xmlRetryService();

    void templateRetryService();
}
