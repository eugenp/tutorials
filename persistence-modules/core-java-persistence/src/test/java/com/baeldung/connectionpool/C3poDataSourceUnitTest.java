package com.baeldung.connectionpool;

import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class C3poDataSourceUnitTest {
    
    @Test
    public void givenC3poDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertTrue(C3poDataSource.getConnection().isValid(1));
    }   
}