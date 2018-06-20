package com.baeldung.si;

import static org.junit.Assert.assertEquals;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class TestSpringIntegrationSecurityIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    SubscribableChannel startDirectChannel;

    @Autowired
    MessageConsumer messageConsumer;

    final String DIRECT_CHANNEL_MESSAGE = "Direct channel message";

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void givenNoUser_whenSendToDirectChannel_thenCredentialNotFound() {
        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
    }

    @Test
    @WithMockUser(username = "jane", roles = { "LOGGER" })
    public void givenRoleLogger_whenSendMessageToDirectChannel_thenAccessDenied() {
        expectedException.expectCause(IsInstanceOf.<Throwable> instanceOf(AccessDeniedException.class));

        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
    }

    @Test
    @WithMockUser(username = "jane")
    public void givenJane_whenSendMessageToDirectChannel_thenAccessDenied() {
        expectedException.expectCause(IsInstanceOf.<Throwable> instanceOf(AccessDeniedException.class));

        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
    }

    @Test
    @WithMockUser(roles = { "VIEWER" })
    public void givenRoleViewer_whenSendToDirectChannel_thenAccessDenied() {
        expectedException.expectCause(IsInstanceOf.<Throwable> instanceOf(AccessDeniedException.class));

        startDirectChannel.send(new GenericMessage<String>(DIRECT_CHANNEL_MESSAGE));
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