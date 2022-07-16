package com.baeldung.scopes;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class ScopesIntegrationTest {

    private static final String NAME = "John Smith";
    private static final String NAME_OTHER = "Anna Jones";

    @Test
    public void givenSingletonScope_whenSetName_thenEqualNames() {
        final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

        final Person personSingletonA = applicationContext.getBean("personSingleton", Person.class);
        final Person personSingletonB = applicationContext.getBean("personSingleton", Person.class);

        personSingletonA.setName(NAME);
        assertEquals(NAME, personSingletonB.getName());

        applicationContext.close();
    }

    @Test
    public void givenPrototypeScope_whenSetNames_thenDifferentNames() {
        final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

        final Person personPrototypeA = applicationContext.getBean("personPrototype", Person.class);
        final Person personPrototypeB = applicationContext.getBean("personPrototype", Person.class);

        personPrototypeA.setName(NAME);
        personPrototypeB.setName(NAME_OTHER);

        assertEquals(NAME, personPrototypeA.getName());
        assertEquals(NAME_OTHER, personPrototypeB.getName());

        applicationContext.close();
    }

}
