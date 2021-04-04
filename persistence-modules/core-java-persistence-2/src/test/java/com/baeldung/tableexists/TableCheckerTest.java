package com.baeldung.tableexists;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableCheckerUnitTest {

    @Test
    void givenCreatedTable_shouldFindTable() throws SQLException, ClassNotFoundException {
        // given
        Connection connection = DatabaseConfig.connect();
        DatabaseConfig.createTables(connection);

        // when
        boolean tableExists = TableChecker.tableExists(connection, "EMPLOYEE");

        // then
        TableChecker.printAllTables(connection, null);
        assertTrue(tableExists);

        DatabaseConfig.dropTables(connection);
    }

    @Test
    void givenCreatedTable_shouldFindTableWithSQL() throws SQLException, ClassNotFoundException {
        // given
        Connection connection = DatabaseConfig.connect();
        DatabaseConfig.createTables(connection);

        // when
        boolean tableExists = TableChecker.tableExistsSQL(connection, "EMPLOYEE");

        // then
        TableChecker.printAllTables(connection, null);
        assertTrue(tableExists);

        DatabaseConfig.dropTables(connection);
    }

    @Test
    void givenNoTable_shouldNotFindTable() throws SQLException, ClassNotFoundException {
        // given
        Connection connection = DatabaseConfig.connect();

        // when
        boolean tableExists = TableChecker.tableExists(connection, "EMPLOYEE");

        // then
        TableChecker.printAllTables(connection, null);
        assertFalse(tableExists);
    }

    @Test
    void givenNoTable_shouldNotFindTableWithSQL() throws SQLException, ClassNotFoundException {
        // given
        Connection connection = DatabaseConfig.connect();

        // when
        boolean tableExists = TableChecker.tableExistsSQL(connection, "EMPLOYEE");

        // then
        TableChecker.printAllTables(connection, null);
        assertFalse(tableExists);
    }
}