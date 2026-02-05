package com.baeldung.data.mariadb4j;

import ch.vorburger.mariadb4j.junit.MariaDB4jRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JUnitRuleLiveTest {
    @Rule
    public MariaDB4jRule dbRule = new MariaDB4jRule(0);

    @Test
    public void whenRunningATest_thenTheDbIsAvailable() throws Exception {
        try (Connection conn = DriverManager.getConnection(dbRule.getURL(), "root", "")) {
            assertFalse(conn.isClosed());
        }
    }
}
