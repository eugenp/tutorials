package com.baeldung.factorybean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanTest {
    
    @Test
    public void testConstructWorkerByXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean-spring-ctx.xml");

        Worker worker = (Worker) context.getBean("worker");
        assertThat(worker.getNumber(), equalTo("1001"));
        assertThat(worker.getTool().getId(), equalTo(1));
        assertThat(worker.getTool().getName(), equalTo("screwdriver"));
        assertThat(worker.getTool().getPrice(), equalTo(1.5));

        ToolFactory toolFactory = (ToolFactory) context.getBean("&tool");
        assertThat(toolFactory.getFactoryId(), equalTo(9090));
    }

    @Test
    public void testConstructWorkerByJava() {
        ApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanAppConfig.class);

        Worker worker = (Worker) context.getBean("worker");
        assertThat(worker.getNumber(), equalTo("1002"));
        assertThat(worker.getTool().getId(), equalTo(2));
        assertThat(worker.getTool().getName(), equalTo("wrench"));
        assertThat(worker.getTool().getPrice(), equalTo(3.7));

        ToolFactory toolFactory = (ToolFactory) context.getBean("&tool");
        assertThat(toolFactory.getFactoryId(), equalTo(7070));
    }
}
