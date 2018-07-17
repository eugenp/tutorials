package com.baeldung.connectionpool;

import com.baeldung.connectionpool.connectionpools.C3poDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class C3poDataSourceUnitTest {
    
    @Test
    public void givenC3poDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertThat(C3poDataSource.getConnection()).isInstanceOf(Connection.class);
    }   
}
