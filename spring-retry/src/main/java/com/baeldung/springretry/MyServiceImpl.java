package com.baeldung.springretry;

import java.net.SocketTimeoutException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MyServiceImpl implements MyService {

    @Override
    public void defaultRetryService() {
        System.out.println("Throw RuntimeException");
        throw new RuntimeException();
    }

    @Override
    public void customRetryService(String sql) throws SocketTimeoutException, SQLException {
        if (StringUtils.isEmpty(sql)) {
            System.out.println("Throw SQLException");
            throw new SQLException();
        } else {
            System.out.println("Throw SocketTimeoutException");
            throw new SocketTimeoutException();
        }
    }

    @Override
    public void recover(SQLException e, String sql) {
        System.out.println("Do recover for SQLException");
        return;
    }

    @Override
    public void xmlRetryService() {
        System.out.println("Throw RuntimeException");
        throw new RuntimeException();
    }

    @Override
    public void templateRetryService() {
        System.out.println("Throw RuntimeException");
        throw new RuntimeException();
    }
}
