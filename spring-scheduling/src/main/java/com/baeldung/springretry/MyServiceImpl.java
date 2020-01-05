package com.baeldung.springretry;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MyServiceImpl implements MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Override
    public void retryService() {
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
    public void recover(SQLException e, String sql) {
        logger.info("In recover method");
    }

    @Override
    public void templateRetryService() {
        logger.info("throw RuntimeException in method templateRetryService()");
        throw new RuntimeException();
    }
}
