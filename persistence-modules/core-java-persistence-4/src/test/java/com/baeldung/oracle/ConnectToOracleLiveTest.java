package com.baeldung.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class ConnectToOracleLiveTest {

    @Ignore
    @Test
    void whenConnectionRetrieved_thenUserNameIsReturned() throws SQLException {

        var url = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
        var userName = "DAN";
        var password = "dan_pw";

        String retrievedUser = null;
        try (var connection = ConnectToOracleDb.getConnection(url, userName, password)) {
            retrievedUser = connection.getMetaData()
                .getUserName();
        }

        assertEquals(userName, retrievedUser);
    }
}
