package com.baeldung.data.mariadb4j;

import ch.vorburger.mariadb4j.junit.MariaDB4jRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JUnitClassRuleLiveTest {
    @ClassRule
    public static MariaDB4jRule dbRule = new MariaDB4jRule(0);

    @Test
    public void whenRunningATest_thenTheDbIsAvailable() throws Exception {
        try (Connection conn = DriverManager.getConnection(dbRule.getURL(), "root", "")) {
            assertFalse(conn.isClosed());
        }
    }

}
