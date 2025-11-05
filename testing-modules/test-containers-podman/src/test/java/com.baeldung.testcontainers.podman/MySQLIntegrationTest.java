package com.baeldung.testcontainers.podman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.mysql.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class MySQLIntegrationTest {

@Test
void whenQueryingDatabase_thenReturnsOne() throws Exception {

    try (MySQLContainer mysql = new MySQLContainer("mysql:8.4")) {
        mysql.start();

        try (Connection conn = DriverManager.getConnection(
                mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword());
             Statement st = conn.createStatement()) {

            st.execute("CREATE TABLE t(id INT PRIMARY KEY)");
            st.execute("INSERT INTO t VALUES (1)");
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM t");
            rs.next();
            Assertions.assertEquals(1, rs.getInt(1));
        }
    }
}

}
