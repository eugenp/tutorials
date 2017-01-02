package com.baeldung.springretry;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MyServiceImpl implements MyService {

    @Override
    public void defaultRetryService() {
        throw new RuntimeException();
    }

    @Override
    public void customRetryService(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            throw new SQLException();
        } 
    }

    @Override
    public void recover(SQLException e, String sql) {
    }

    @Override
    public void xmlRetryService() {
        throw new RuntimeException();
    }

    @Override
    public void templateRetryService() {
        throw new RuntimeException();
    }
}
