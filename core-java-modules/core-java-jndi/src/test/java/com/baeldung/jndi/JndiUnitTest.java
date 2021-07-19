package com.baeldung.jndi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.*;
import javax.sql.DataSource;

import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

class JndiUnitTest {

    private static InitialContext ctx;
    private static DriverManagerDataSource ds;

    @BeforeAll
    static void setUp() throws Exception {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        ds = new DriverManagerDataSource("jdbc:h2:mem:mydb");
        builder.activate();

        JndiTemplate jndiTemplate = new JndiTemplate();
        ctx = (InitialContext) jndiTemplate.getContext();
    }

    @Test
    void givenACompositeName_whenAddingAnElement_thenNameIncludesIt() throws Exception {
        Name objectName = new CompositeName("java:comp/env/jdbc");

        Enumeration<String> elements = objectName.getAll();
        while(elements.hasMoreElements()) {
            System.out.println(elements.nextElement());
        }

        objectName.add("example");

        assertEquals("env", objectName.get(1));
        assertEquals("example", objectName.get(objectName.size() - 1));
    }

    @Test
    void givenADataSource_whenAddingDriver_thenBind() throws Exception {
        ds.setDriverClassName("org.h2.Driver");
        ctx.bind("java:comp/env/jdbc/datasource", ds);
    }

    @Test
    void givenContext_whenLookupByName_thenValidDataSource() throws Exception {
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/datasource");

        assertNotNull(ds);
        assertNotNull(ds.getConnection());
    }

}
