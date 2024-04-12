package com.baeldung.connectionpool;

import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HikariCPDataSourceUnitTest {
    
    @Test
    public void givenHikariDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertTrue(HikariCPDataSource.getConnection().isValid(1));
    }   
}