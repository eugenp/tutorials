package com.baeldung.beaninjection.setterbased;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.beaninjection.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:setterbeaninjection-context.xml" })
public class SetterBasedBeanInjectionWithXMLIntegrationTest {

    @Autowired
    private MessageSender messageSender;

    @Test
    public void messageSenderShouldTriggerSendingMessage() {
        messageSender.sendMessage();
    }

    @Test
    public void sendMessageCallVerification() {
        TextMessage message = mock(TextMessage.class);
        MessageSender messageSender1 = new MessageSender();
        messageSender1.setMessage(message);
        messageSender1.sendMessage();
        verify(message, times(1)).send();
    }
}
