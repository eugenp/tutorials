package com.insightsource.pool.jdbc;

import com.insightsource.pool.Pool.Validator;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectionValidator implements Validator<Connection> {

    @Override
    public boolean isValid(Connection t) {
        if (t == null) {
            return false;
        }

        try {
            return !t.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void invalidate(Connection t) {
        try {
            t.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
