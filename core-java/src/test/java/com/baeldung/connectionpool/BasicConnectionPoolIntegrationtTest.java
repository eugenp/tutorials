package com.baeldung.connectionpool;

import com.baeldung.connectionpool.connectionpools.BasicConnectionPool;
import com.baeldung.connectionpool.connectionpools.ConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicConnectionPoolIntegrationtTest {
    
    private static ConnectionPool connectionPool;
    
    
    @BeforeClass
    public static void setUpBasicConnectionPoolInstance() throws SQLException {
        connectionPool = BasicConnectionPool.createFromDriverManager("jdbc:mysql://localhost:3306/mydb", "user", "password");
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetConnection_thenCorrect() {
        assertThat(connectionPool.getConnection(1)).isInstanceOf(Connection.class);
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetConnectionPool_thenCorrect() {
        assertThat(connectionPool.getConnectionPool()).isInstanceOf(List.class);
    }
}