package com.baeldung.si;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.si.security.MessageConsumer;
import com.baeldung.si.security.SecurityConfig;
import com.baeldung.si.security.SecurityPubSubChannel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SecurityPubSubChannel.class, MessageConsumer.class, SecurityConfig.class })
public class TestSpringIntegrationSecurityExecutorIntegrationTest {

    @Autowired
    SubscribableChannel startPSChannel;

    @Autowired
    MessageConsumer messageConsumer;

    @Autowired
    ThreadPoolTaskExecutor executor;

    final String DIRECT_CHANNEL_MESSAGE = "Direct channel message";

    @Before
    public void clearData() {
        messageConsumer.setMessagePSContent(new ConcurrentHashMap<>());
        executor.setWaitForTasksToCompleteOnShutdown(true);
    }

    @Test
    @WithMockUser(username = "user", roles = { "VIEWER" })
    public void givenRoleUser_whenSendMessageToPSChannel_thenNoMessageArrived() throws IllegalStateException, InterruptedException {
        startPSChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));

        executor.getThreadPoolExecutor().awaitTermination(2, TimeUnit.SECONDS);

        assertEquals(1, messageConsumer.getMessagePSContent().size());
        assertTrue(messageConsumer.getMessagePSContent().values().contains("user"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "LOGGER", "VIEWER" })
    public void givenRoleUserAndLogger_whenSendMessageToPSChannel_then2GetMessages() throws IllegalStateException, InterruptedException {
        startPSChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));

        executor.getThreadPoolExecutor().awaitTermination(2, TimeUnit.SECONDS);

        assertEquals(2, messageConsumer.getMessagePSContent().size());
        assertTrue(messageConsumer.getMessagePSContent().values().contains("user"));
        assertTrue(messageConsumer.getMessagePSContent().values().contains("ROLE_LOGGER,ROLE_VIEWER"));
    }

}
