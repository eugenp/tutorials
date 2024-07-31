package com.baeldung.statmentVsPreparedstatment;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatasourceFactoryUnitTest {

    @Test
    void whenCreateConnectionAndTables_thenConnectionIsOpenAndTableIsCreated()
        throws SQLException, ClassNotFoundException {
        DatasourceFactory factory = new DatasourceFactory();
        Connection connection = factory.getConnection();

        assertFalse(connection.isClosed());
        assertTrue(factory.createTables());
    }
}