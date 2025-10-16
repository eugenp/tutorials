package com.baeldung.springretry;

import java.sql.SQLException;
import reactor.core.publisher.Mono; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

@Service
public class MyServiceImpl implements MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Override
    public void retryService() {
        logger.info("Retry Number: " + RetrySynchronizationManager.getContext()
            .getRetryCount());
        logger.info("throw RuntimeException in method retryService()");
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

    // **NEW FEATURE: Reactive implementation**
    private int reactiveAttemptCount = 0;

    @Override
    public Mono<String> reactiveRetryService() {
        return Mono.defer(() -> {
            logger.info("Reactive Retry Number: " + (reactiveAttemptCount++));
            if (reactiveAttemptCount < 3) {
                logger.info("throw RuntimeException in method reactiveRetryService()");
                return Mono.error(new RuntimeException("Transient Reactive Error"));
            }
            return Mono.just("Reactive Success");
        });
    }
    
    // **NEW RECOVER METHOD FOR REACTIVE TYPE**
    @Override
    public Mono<String> recover(RuntimeException e) {
        logger.info("In reactive recover method for RuntimeException");
        return Mono.just("Reactive Fallback Success");
    }
}
