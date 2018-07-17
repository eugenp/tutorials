package com.baeldung.connectionpool;

import com.baeldung.connectionpool.connectionpools.DBCPDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class DBCPDataSourceUnitTest {
    
    @Test
    public void givenDBCPDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertThat(DBCPDataSource.getConnection()).isInstanceOf(Connection.class);
    }   
}
