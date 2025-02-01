package com.baeldung.exception.abstractmethoderror;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractMethodErrorUnitTest {
    private static final String url = "jdbc:h2:mem:A-DATABASE;INIT=CREATE SCHEMA IF NOT EXISTS myschema";
    private static final String username = "sa";

    @Test
    void givenOldH2Database_whenCallgetSchemaMethod_thenThrowAbstractMethodError() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, "");
        assertNotNull(conn);
        Assertions.assertThrows(AbstractMethodError.class, () -> conn.getSchema());
    }
}
