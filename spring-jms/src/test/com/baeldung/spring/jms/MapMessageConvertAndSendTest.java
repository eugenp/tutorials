package com.baeldung.spring.jms;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.jms.Employee;
import com.baeldung.spring.jms.SampleJmsMessageSender;

public class MapMessageConvertAndSendTest {

    private SampleJmsMessageSender messageProducer;

    @SuppressWarnings("resource")
    @Before
    public void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath:EmbeddedActiveMQ.xml", "classpath:applicationContext.xml" });
        messageProducer = (SampleJmsMessageSender) applicationContext.getBean("SampleJmsMessageSender");
    }

    @Test
    public void testSendMessage() {
        messageProducer.sendMessage(new Employee("JavaDeveloper2", 22));
    }

}
