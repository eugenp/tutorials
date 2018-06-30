package com.baeldung.extensions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.baeldung.helpers.EmployeeJdbcDao;
import com.baeldung.helpers.JdbcConnectionUtil;

public class EmployeeDatabaseSetupExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private Connection con = JdbcConnectionUtil.getConnection();
    private EmployeeJdbcDao employeeDao = new EmployeeJdbcDao(con);
    private Savepoint savepoint;

    @Override
    public void afterAll(ExtensionContext context) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) throws SQLException {
        employeeDao.createTable();

    }

    @Override
    public void afterEach(ExtensionContext context) throws SQLException {
        con.rollback(savepoint);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws SQLException {
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("before");
    }

}
