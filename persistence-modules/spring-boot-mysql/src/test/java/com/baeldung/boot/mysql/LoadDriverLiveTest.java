package com.baeldung.boot.mysql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//Please note, this test requires a MySQL server running on localhost:3306 with database "test" already created.
@SpringBootTest
@ActiveProfiles("dev1")
class LoadDriverLiveTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void whenConnectingToDatabase_thenConnectionShouldBeValid() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
        }
    }
}
