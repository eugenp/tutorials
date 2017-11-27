package com.baeldung.di.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Demo that showcases the different types of dependency injection supported by Spring. They are:
 * 
 * <pre>
 * 1. Constructor injection
 * 2. Setter injection
 * 3. Field injection</pre>
 * 
 * For each type, this demo shows both the annotation and XML configuration.
 * <br/><br/>
 * You can read the full article <a href="http://inprogress.baeldung.com/?p=63434">here</a>.
 * 
 * @author Donato Rimenti
 * @see <a href="http://inprogress.baeldung.com/?p=63434">Full article</a>
 *
 */
public class DependencyInjectionTypesDemo {

    /**
     * Logger.
     */
    private final static Logger LOG = LoggerFactory.getLogger(DependencyInjectionTypesDemo.class);

    /**
     * Main method.
     * 
     * @param args null
     */
    public static void main(String[] args) {
        // Initializes the context.
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("dependency-injection-types-demo-context.xml");

        // Gets a bean with autowired fields and logs their values.
        AutowiredInjectionExample autowiredInjectionExample = (AutowiredInjectionExample) context.getBean(AutowiredInjectionExample.class);
        LOG.info("Injection with annotations");
        LOG.info("Value of field injected by constructor annotation: [{}]", autowiredInjectionExample.getConstructorInjection());
        LOG.info("Value of field injected by setter annotation: [{}]", autowiredInjectionExample.getSetterInjection());
        LOG.info("Value of field injected by field annotation: [{}]", autowiredInjectionExample.getFieldInjection());

        // Gets a bean with a value injected through constructor and logs its variables' values.
        XmlInjectionExample xmlConstructorInjection = (XmlInjectionExample) context.getBean("xmlConstructorInjectionExample");
        LOG.info("Constructor injection with XML");
        LOG.info("Value of field injected by constructor annotation: [{}]", xmlConstructorInjection.getConstructorInjection());
        LOG.info("Value of field injected by setter annotation: [{}]", xmlConstructorInjection.getSetterInjection());

        // Gets a bean with a value injected through setter and logs its variables' values.
        XmlInjectionExample xmlSetterInjection = (XmlInjectionExample) context.getBean("xmlSetterInjectionExample");
        LOG.info("Setter injection with XML");
        LOG.info("Value of field injected by constructor annotation: [{}]", xmlSetterInjection.getConstructorInjection());
        LOG.info("Value of field injected by setter annotation: [{}]", xmlSetterInjection.getSetterInjection());

        // Closes the Spring context.
        context.close();
    }

}
