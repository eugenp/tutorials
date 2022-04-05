package com.baeldung.spring.jndi.datasource.mock;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleJNDIUnitTest {

    private InitialContext initContext;

    @BeforeEach
    public void setup() throws Exception {
        this.initContext = new InitialContext();
    }

    @Test
    public void whenMockJndiDataSource_thenReturnJndiDataSource() throws Exception {
        String dsString = "org.h2.Driver::::jdbc:jdbc:h2:mem:testdb::::sa";
        Context envContext = (Context) this.initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("datasource/ds");

        assertEquals(dsString, ds.toString());
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (this.initContext != null) {
            this.initContext.close();
            this.initContext = null;
        }
    }

}
