package com.baeldung.connectionpool;

import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class C3p0DataSourceUnitTest {
    
    @Test
    public void givenC3p0DataSourceClass_whenCallGetConnection_thenCorrect() throws SQLException {
        assertTrue(C3p0DataSource.getConnection().isValid(1));
    }   
}