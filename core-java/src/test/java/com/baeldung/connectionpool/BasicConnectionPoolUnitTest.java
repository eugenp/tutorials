package com.baeldung.connectionpool;

import com.baeldung.connectionpool.connectionpools.BasicConnectionPool;
import com.baeldung.connectionpool.connectionpools.ConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicConnectionPoolUnitTest {
    
    private static ConnectionPool connectionPool;
    
    
    @BeforeClass
    public static void setUpBasicConnectionPoolInstance() throws SQLException {
        connectionPool = BasicConnectionPool.create("jdbc:h2:mem:test", "root", "sawstudio2567");
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetConnection_thenCorrect() throws SQLException {
        assertTrue(connectionPool.getConnection().isValid(1));
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledreleaseConnection_thenCorrect() {
        Connection connection = connectionPool.getConnection();
        assertThat(connectionPool.releaseConnection(connection)).isTrue();
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetConnectionPool_thenCorrect() {
        assertThat(connectionPool.getConnectionPool()).isInstanceOf(List.class);
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetUrl_thenCorrect() {
        assertThat(connectionPool.getUrl()).isEqualTo("jdbc:h2:mem:test");
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetUser_thenCorrect() {
        assertThat(connectionPool.getUser()).isEqualTo("root");
    }
    
    @Test
    public void givenBasicConnectionPoolInstance_whenCalledgetPassword_thenCorrect() {
        assertThat(connectionPool.getPassword()).isEqualTo("sawstudio2567");
    }
}