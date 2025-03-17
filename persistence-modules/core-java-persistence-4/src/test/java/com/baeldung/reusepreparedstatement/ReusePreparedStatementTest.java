package com.baeldung.reusepreparedstatement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.baeldung.resusepreparedstatement.ReusePreparedStatement;

class ReusePreparedStatementTest {

    @Test
    void whenCallingInefficientPreparedStatementMethod_thenRowsAreCreatedAsExpected() throws SQLException {
        ReusePreparedStatement service = new ReusePreparedStatement();
        service.setupDatabaseAndConnect();
        service.inefficientUsage();
        int rowsCreated = service.checkRowCount();
        service.destroyDB();
        assertEquals(10000, rowsCreated);
    }

    @Test
    void whenCallingBetterPreparedStatementMethod_thenRowsAreCreatedAsExpected() throws SQLException {
        ReusePreparedStatement service = new ReusePreparedStatement();
        service.setupDatabaseAndConnect();
        service.betterUsage();
        int rowsCreated = service.checkRowCount();
        service.destroyDB();
        assertEquals(10000, rowsCreated);
    }

    @Test
    void whenCallingBestPreparedStatementMethod_thenRowsAreCreatedAsExpected() throws SQLException {
        ReusePreparedStatement service = new ReusePreparedStatement();
        service.setupDatabaseAndConnect();
        service.bestUsage();
        int rowsCreated = service.checkRowCount();
        service.destroyDB();
        assertEquals(10000, rowsCreated);
    }

}
