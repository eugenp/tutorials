package com.baeldung.jndi.datasource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@SuppressWarnings("deprecation")
public class SimpleNamingContextBuilderUnitTest {
    
    private InitialContext initContext;

    @BeforeEach
    public void init() throws Exception {
        SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        this.initContext = new InitialContext();
    }

    @Test
    public void whenMockJndiDataSource_thenReturnJndiDataSource() throws Exception {
        this.initContext.bind("java:comp/env/jdbc/datasource", new DriverManagerDataSource("jdbc:h2:mem:testdb"));
        DataSource ds = (DataSource) this.initContext.lookup("java:comp/env/jdbc/datasource");

        assertNotNull(ds.getConnection());
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        this.initContext.close();
    }

}
