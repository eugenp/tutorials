package com.baeldung.getdburl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class DBConfigurationUnitTest {

    @Test
    void givenConnectionObject_whenExtractMetaData_thenGetDbURL() throws Exception {
        Connection connection = DBConfiguration.getConnection();
        String dbUrl = connection.getMetaData().getURL();
        assertEquals("jdbc:h2:mem:testdb", dbUrl);
    }

}
