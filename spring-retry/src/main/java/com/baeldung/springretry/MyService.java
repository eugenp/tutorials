package com.baeldung.springretry;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface MyService {

    @Retryable
    void defaultRetryService();

    @Retryable(value = { IOException.class, SQLException.class }, maxAttempts = 2, backoff = @Backoff(delay = 5000))
    void customRetryService(String sql) throws SocketTimeoutException, SQLException;

    @Recover
    void recover(SQLException e, String sql);
    
    void xmlRetryService();
    
    void templateRetryService();
}
