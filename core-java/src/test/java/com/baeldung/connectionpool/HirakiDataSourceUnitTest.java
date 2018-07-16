package com.baeldung.connectionpool;

import com.baeldung.connectionpool.connectionpools.HirakiDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class HirakiDataSourceUnitTest {
    
    @Test
    public void givenHirakiDataSourceClass_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertThat(HirakiDataSource.getConnection()).isInstanceOf(Connection.class);
    }   
}
