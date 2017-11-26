package com.baeldung.di.types;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit tests for {@link DependencyInjectionTypesDemo}.
 * 
 * @author Donato Rimenti
 *
 */
public class DependencyInjectionTypesDemoUnitTest {

    /**
     * Spring context used for this test.
     */
    private AbstractApplicationContext context;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        this.context = new ClassPathXmlApplicationContext("dependency-injection-types-demo-context.xml");
    }

    /**
     * Checks that the autowired annotation injection is performed correctly.
     */
    @Test
    public void whenAutowiredInjection_thenInjectionSuccesfull() {
        AutowiredInjectionExample autowiredInjection = (AutowiredInjectionExample) context.getBean("autowiredInjectionExample");

        // Checks that the bean is not null and the fields are correctly injected.
        Assert.assertNotNull(autowiredInjection);
        Assert.assertNotNull(autowiredInjection.getConstructorInjection());
        Assert.assertNotNull(autowiredInjection.getSetterInjection());
        Assert.assertNotNull(autowiredInjection.getFieldInjection());
    }

    /**
     * Checks that the XML constructor injection is performed correctly.
     */
    @Test
    public void whenXmlConstructorInjection_thenInjectionSuccesfull() {
        XmlInjectionExample xmlConstructorInjection = (XmlInjectionExample) context.getBean("xmlConstructorInjectionExample");

        // Checks that the bean is not null and that only the constructor injection is performed.
        Assert.assertNotNull(xmlConstructorInjection);
        Assert.assertNotNull(xmlConstructorInjection.getConstructorInjection());
        Assert.assertNull(xmlConstructorInjection.getSetterInjection());
    }

    /**
     * Checks that the XML setter injection is performed correctly.
     */
    @Test
    public void whenXmlSetterInjection_thenInjectionSuccesfull() {
        XmlInjectionExample xmlSetterInjection = (XmlInjectionExample) context.getBean("xmlSetterInjectionExample");

        // Checks that the bean is not null and that only the setter injection is performed.
        Assert.assertNotNull(xmlSetterInjection);
        Assert.assertNull(xmlSetterInjection.getConstructorInjection());
        Assert.assertNotNull(xmlSetterInjection.getSetterInjection());
    }

    /**
     * Destroys the test.
     */
    @After
    public void tearUp() {
        context.close();
    }

}
