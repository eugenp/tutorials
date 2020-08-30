package com.baeldung.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDriverLoadingUnitTest {

    @Before
    public void setup() throws SQLException {
        if (!org.postgresql.Driver.isRegistered()) {
            org.postgresql.Driver.register();
        }
    }
    /**
     * Driver is registered automatically with the service provider mechanism.
     */
    @Test
    public void givenJdbcDriverWithSP_whenGettingDriver_thenDriverIsReturned() throws SQLException {
        Driver driver = getDriver();
        assertNotNull("Expected initialized postgres driver", driver);
    }

    /**
     * Driver is registered automatically, then deregistered.
     * @throws SQLException
     */
    @Test(expected = SQLException.class)
    public void givenRegisteredJdbcDriver_whenDeregister_thenNoDriverIsReturned() throws SQLException {
        Driver driver = getDriver();
        DriverManager.deregisterDriver(driver);
        driver = getDriver();
        assertNull(driver);
    }

    /**
     * Driver is automatically by the JVM, then deregistered, then registered again.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void givenNoRegisteredDriver_whenClassForName_thenDriverIsReturned() throws SQLException, ClassNotFoundException {
        deregisterDriver();

        Driver driver = null;
        try {
            driver = getDriver();
        }
        catch (SQLException e) {
            // expected
        }
        assertNull("Must fail on attempt to get a deregistered driver", driver);

        org.postgresql.Driver.register(); // This is invoked as part of Class.forName() logic

        driver = getDriver();
        assertNotNull("Driver must register as part of class loading", driver);
    }

    private void deregisterDriver() throws SQLException {
        org.postgresql.Driver.deregister();
    }

    private Driver getDriver() throws SQLException {
        return DriverManager.getDriver("jdbc:postgresql:");
    }
}
