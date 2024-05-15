package com.baeldung.jndi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.*;
import javax.sql.DataSource;

import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

class JndiNamingUnitTest {

    private static InitialContext context;
    private static DriverManagerDataSource dataSource;

    @BeforeAll
    static void setUp() throws Exception {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:mydb");
        builder.activate();

        JndiTemplate jndiTemplate = new JndiTemplate();
        context = (InitialContext) jndiTemplate.getContext();
        
        dataSource.setDriverClassName("org.h2.Driver");
        context.bind("java:comp/env/jdbc/datasource", dataSource);
    }

    @Test
    void givenACompositeName_whenAddingAnElement_thenNameIsAdded() throws Exception {
        Name objectName = new CompositeName("java:comp/env/jdbc");

        Enumeration<String> items = objectName.getAll();
        while(items.hasMoreElements()) {
            System.out.println(items.nextElement());
        }

        objectName.add("New Name");

        assertEquals("env", objectName.get(1));
        assertEquals("New Name", objectName.get(objectName.size() - 1));
    }

    @Test
    void givenContext_whenLookupByName_thenReturnsValidObject() throws Exception {
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/datasource");

        assertNotNull(ds);
        assertNotNull(ds.getConnection());
    }
    
    @Test
    void givenSubContext_whenLookupByName_thenReturnsValidObject() throws Exception {
        Context subContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) subContext.lookup("jdbc/datasource");

        assertNotNull(ds);
        assertNotNull(ds.getConnection());
    }
    
    @AfterAll
    static void tearDown() throws Exception {
       context.close();
    }

}
