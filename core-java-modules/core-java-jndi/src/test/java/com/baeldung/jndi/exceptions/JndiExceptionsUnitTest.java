package com.baeldung.jndi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NoInitialContextException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JndiExceptionsUnitTest {

    @Test
    public void testNoInitialContextException() {
        assertThrows(NoInitialContextException.class, () -> {
            JndiTemplate jndiTemplate = new JndiTemplate();
            InitialContext ctx = (InitialContext) jndiTemplate.getContext();
            ctx.lookup("java:comp/env/jdbc/datasource");
        }).printStackTrace();
    }

    @Test
    public void testNameNotFoundException() {
        assertThrows(NameNotFoundException.class, () -> {
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.activate();

            JndiTemplate jndiTemplate = new JndiTemplate();
            InitialContext ctx = (InitialContext) jndiTemplate.getContext();
            ctx.lookup("badJndiName");
        }).printStackTrace();
    }

}
