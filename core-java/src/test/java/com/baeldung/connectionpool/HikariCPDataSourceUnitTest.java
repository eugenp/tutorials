package com.baeldung.connectionpool.test;

import com.baeldung.connectionpool.connectionpools.HikariCPDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class HikariCPDataSourceUnitTest {
    
    @Test
    public void givenHikariDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertThat(HikariCPDataSource.getConnection()).isInstanceOf(Connection.class);
    }   
}
