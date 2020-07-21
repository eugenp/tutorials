package com.baeldung.mockito.argumentcaptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceUnitTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    DeliveryPlatform platform;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Captor
    ArgumentCaptor<Credentials> credentialsCaptor;

    @Test
    public void whenDoesNotSupportHtml_expectTextOnlyEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "Hey, let'use ArgumentCaptor";

        emailService.send(to, subject, body, false);

        Mockito.verify(platform).send(emailCaptor.capture());
        Email emailCaptorValue = emailCaptor.getValue();
        assertEquals(Format.TEXT_ONLY, emailCaptorValue.getFormat());
    }

    @Test
    public void whenDoesSupportHtml_expectHTMLEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "<html><body>Hey, let'use ArgumentCaptor</body></html>";

        emailService.send(to, subject, body, true);

        Mockito.verify(platform).send(emailCaptor.capture());
        Email value = emailCaptor.getValue();
        assertEquals(Format.HTML, value.getFormat());
    }

    @Test
    public void whenServiceRunning_expectUpResponse() {
        Mockito.when(platform.getServiceStatus()).thenReturn("OK");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertEquals(ServiceStatus.UP, serviceStatus);
    }

    @Test
    public void whenServiceNotRunning_expectDownResponse() {
        Mockito.when(platform.getServiceStatus()).thenReturn("Error");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertEquals(ServiceStatus.DOWN, serviceStatus);
    }

    @Test
    public void usingArgumentMatcher_whenAuthenticatedWithValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        Mockito.when(platform.authenticate(Mockito.eq(credentials))).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
    }

    @Test
    public void usingArgumentCaptor_whenAuthenticatedWithValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        Mockito.when(platform.authenticate(credentialsCaptor.capture())).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
        assertEquals(credentials, credentialsCaptor.getValue());
    }

    @Test
    public void whenNotAuthenticated_expectFalse() {
        Credentials credentials = new Credentials("baeldung", "incorrect_password", "incorrect_key");
        Mockito.when(platform.authenticate(Mockito.eq(credentials))).thenReturn(AuthenticationStatus.NOT_AUTHENTICATED);

        assertFalse(emailService.authenticatedSuccessfully(credentials));
    }
}