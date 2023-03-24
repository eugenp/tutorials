package com.baeldung.spring.jms;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DefaultTextMessageSenderIntegrationTest {

    private static SampleJmsMessageSender messageProducer;
    private static SampleListener messageListener;

    @SuppressWarnings("resource")
    @BeforeClass
    public static void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:EmbeddedActiveMQ.xml", "classpath:applicationContext.xml");
        messageProducer = (SampleJmsMessageSender) applicationContext.getBean("SampleJmsMessageSender");
        messageListener = (SampleListener) applicationContext.getBean("messageListener");
    }

    @Test
    public void testSimpleSend() {
        messageProducer.simpleSend();
    }

    @Test
    public void testSendTextMessage() {
        messageProducer.sendTextMessage(null);
    }

}
