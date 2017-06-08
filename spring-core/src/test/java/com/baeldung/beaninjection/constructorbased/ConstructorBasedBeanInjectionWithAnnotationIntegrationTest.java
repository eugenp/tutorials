package com.baeldung.beaninjection.constructorbased;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfig.class)
public class ConstructorBasedBeanInjectionWithAnnotationIntegrationTest {

    @Autowired
    private MessageSender messageSender;

    @Test
    public void messageSenderShouldTriggerSendingMessage() {
        messageSender.sendMessage();
    }

    @Test
    public void messageSendCallVerification() {
        TextMessage message = mock(TextMessage.class);
        MessageSender messageSender1 = new MessageSender(message);
        messageSender1.sendMessage();
        verify(message, times(1)).send();
    }

}
