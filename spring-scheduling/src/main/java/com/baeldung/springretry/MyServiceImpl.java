package com.baeldung.springretry;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.resilience.annotation.ConcurrencyLimit;
@Service
public class MyServiceImpl implements MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Override
    public void retryService() {
        // This is correct for logging the retry count (0 on first attempt, 1 on first retry, etc.)
        logger.info("Retry Number: " + RetrySynchronizationManager.getContext()
            .getRetryCount());
        logger.info("throw RuntimeException in method retryService()");
        // Always throws the exception to force the retry aspect to kick in
        throw new RuntimeException();
    }

    @Override
    public void retryServiceWithRecovery(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            logger.info("throw SQLException in method retryServiceWithRecovery()");
            throw new SQLException();
        }
    }

    @Override
    public void retryServiceWithCustomization(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            logger.info("throw SQLException in method retryServiceWithCustomization()");
            // Correctly throws SQLException to trigger the 2 retry attempts
            throw new SQLException();
        }
    }

    @Override
    public void retryServiceWithExternalConfiguration(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            logger.info("throw SQLException in method retryServiceWithExternalConfiguration()");
            throw new SQLException();
        }
    }

    @Override
    public void recover(SQLException e, String sql) {
        logger.info("In recover method");
    }

    @Override
    public void templateRetryService() {
        logger.info("throw RuntimeException in method templateRetryService()");
        throw new RuntimeException();
    }

    // **NEW Implementation for Concurrency Limit**
    @Override
    @ConcurrencyLimit(5)
    public void concurrentLimitService() {
        // The ConcurrencyLimit aspect will wrap this method.
        logger.info("Concurrency Limit Active. Current Thread: " + Thread.currentThread().getName());
        // Simulate a time-consuming task to observe throttling
        try {
            Thread.sleep(1000); // Correctly blocks to hold the lock
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Concurrency Limit Released. Current Thread: " + Thread.currentThread().getName());
    }
}
