package com.baeldung.jndi.datasource;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleJNDIUnitTest {

    private InitialContext initContext;

    @BeforeEach
    public void setup() throws Exception {
        System.setProperty("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
        System.setProperty("org.osjava.sj.root", "file://src/test/resources");
        System.setProperty("org.osjava.sj.delimiter", ".");
        System.setProperty("jndi.syntax.separator", "/");
        System.setProperty("org.osjava.sj.space", "java:/comp/env");

        this.initContext = new InitialContext();
    }

    @Test
    public void whenMockJndiDataSource_thenReturnJndiDataSource() throws Exception {
        String dsString = "org.h2.Driver::::jdbc:jdbc:h2:mem:testdb::::sa";

        Context envContext = (Context) this.initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("datasource/ds");

        assertEquals(dsString, ds.toString());
    }

}