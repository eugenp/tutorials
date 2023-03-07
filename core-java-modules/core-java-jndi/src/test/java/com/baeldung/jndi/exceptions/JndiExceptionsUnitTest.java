package com.baeldung.jndi.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NoInitialContextException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JndiExceptionsUnitTest {
    
    InitialContext ctx;

    @Test
    @Order(1)
    void givenNoContext_whenLookupObject_thenThrowNoInitialContext() {
        assertThrows(NoInitialContextException.class, () -> {
            JndiTemplate jndiTemplate = new JndiTemplate();
            ctx = (InitialContext) jndiTemplate.getContext();
            ctx.lookup("java:comp/env/jdbc/datasource");
            ctx.close();            
        }).printStackTrace();
    }

    @Test
    @Order(2)
    void givenEmptyContext_whenLookupNotBounds_thenThrowNameNotFound() {
        assertThrows(NameNotFoundException.class, () -> {
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.activate();

            JndiTemplate jndiTemplate = new JndiTemplate();
            ctx = (InitialContext) jndiTemplate.getContext();
            ctx.lookup("badJndiName");
            ctx.close();
        }).printStackTrace();
    }

}
