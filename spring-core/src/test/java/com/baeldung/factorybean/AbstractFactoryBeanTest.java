package com.baeldung.factorybean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractFactoryBeanTest {
    
    @Test
    public void testSingleToolFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean-abstract-spring-ctx.xml");

        Worker worker1 = (Worker) context.getBean("worker1");
        Worker worker2 = (Worker) context.getBean("worker2");

        assertThat(worker1.getNumber(), equalTo("50001"));
        assertThat(worker2.getNumber(), equalTo("50002"));
        assertTrue(worker1.getTool() == worker2.getTool());
    }

    @Test
    public void testNonSingleToolFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean-abstract-spring-ctx.xml");

        Worker worker3 = (Worker) context.getBean("worker3");
        Worker worker4 = (Worker) context.getBean("worker4");

        assertThat(worker3.getNumber(), equalTo("50003"));
        assertThat(worker4.getNumber(), equalTo("50004"));
        assertTrue(worker3.getTool() != worker4.getTool());
    }
}
