package com.baeldung.si;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.si.security.MessageConsumer;
import com.baeldung.si.security.SecuredDirectChannel;
import com.baeldung.si.security.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SecurityConfig.class, SecuredDirectChannel.class, MessageConsumer.class })
public class TestSpringIntegrationSecurity {

    @Autowired
    SubscribableChannel startDirectChannel;

    @Autowired
    MessageConsumer messageConsumer;

    final String DIRECT_CHANNEL_MESSAGE = "Direct channel message";

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void givenNoUser_whenSendToDirectChannel_thenCredentialNotFound() {
        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "jane", roles = { "LOGGER" })
    public void givenRoleLogger_whenSendMessageToDirectChannel_thenAccessDenied() throws Throwable {
        try {
            startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "jane")
    public void givenJane_whenSendMessageToDirectChannel_thenAccessDenied() throws Throwable {
        try {
            startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = { "VIEWER" })
    public void givenRoleViewer_whenSendToDirectChannel_thenAccessDenied() throws Throwable {
        try {
            startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test
    @WithMockUser(roles = { "LOGGER", "VIEWER", "EDITOR" })
    public void givenRoleLoggerAndUser_whenSendMessageToDirectChannel_thenFlowCompletedSuccessfully() {
        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
        assertEquals(DIRECT_CHANNEL_MESSAGE, messageConsumer.getMessageContent());
    }

    @Test
    @WithMockUser(username = "jane", roles = { "LOGGER", "EDITOR" })
    public void givenJaneLoggerEditor_whenSendToDirectChannel_thenFlowCompleted() {
        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
        assertEquals(DIRECT_CHANNEL_MESSAGE, messageConsumer.getMessageContent());
    }

}
