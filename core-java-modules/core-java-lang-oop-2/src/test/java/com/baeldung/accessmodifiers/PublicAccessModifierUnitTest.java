package com.baeldung.accessmodifiers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@TestInstance(Lifecycle.PER_CLASS)
public class PublicAccessModifierUnitTest {

    @Test
    public void whenUsingIntValue_valuesAreEqual() {
        
        assertEquals(0, new BigDecimal(0).intValue());
    }

    @Test
    public void whenUsingToLowerCase_valuesAreEqual() {
        
        assertEquals("alex", "ALEX".toLowerCase());
    }

    @Test
    public void whenConnectingToH2_connectionInstanceIsReturned() throws SQLException {

        final String URL = "jdbc:h2:~/test";
        Connection conn = DriverManager.getConnection(URL, "sa", "");
        assertNotNull(conn);
    }

}
