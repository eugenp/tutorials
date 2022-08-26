package com.baeldung.mockito.argumentcaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceUnitTest {

    @Mock
    DeliveryPlatform platform;

    @InjectMocks
    EmailService emailService;

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

        Mockito.verify(platform).deliver(emailCaptor.capture());
        Email emailCaptorValue = emailCaptor.getValue();
        assertThat(emailCaptorValue.getFormat()).isEqualTo(Format.TEXT_ONLY);
    }

    @Test
    public void whenDoesSupportHtml_expectHTMLEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "<html><body>Hey, let'use ArgumentCaptor</body></html>";

        emailService.send(to, subject, body, true);

        Mockito.verify(platform).deliver(emailCaptor.capture());
        Email value = emailCaptor.getValue();
        assertThat(value.getFormat()).isEqualTo(Format.HTML);
    }

    @Test
    public void whenServiceRunning_expectUpResponse() {
        Mockito.when(platform.getServiceStatus()).thenReturn("OK");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertThat(serviceStatus).isEqualTo(ServiceStatus.UP);
    }

    @Test
    public void whenServiceNotRunning_expectDownResponse() {
        Mockito.when(platform.getServiceStatus()).thenReturn("Error");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertThat(serviceStatus).isEqualTo(ServiceStatus.DOWN);
    }

    @Test
    public void whenUsingArgumentMatcherForValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        Mockito.when(platform.authenticate(Mockito.eq(credentials))).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
    }

    @Test
    public void whenUsingArgumentCaptorForValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        Mockito.when(platform.authenticate(credentialsCaptor.capture())).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
        assertThat(credentialsCaptor.getValue()).isEqualTo(credentials);
    }

    @Test
    public void whenNotAuthenticated_expectFalse() {
        Credentials credentials = new Credentials("baeldung", "incorrect_password", "incorrect_key");
        Mockito.when(platform.authenticate(Mockito.eq(credentials))).thenReturn(AuthenticationStatus.NOT_AUTHENTICATED);

        assertFalse(emailService.authenticatedSuccessfully(credentials));
    }
}