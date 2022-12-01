package com.baeldung.mockito.argumentcaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceUnitTest {

    @Mock
    DeliveryPlatform platform;

    @InjectMocks
    EmailService emailService;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Captor
    ArgumentCaptor<Credentials> credentialsCaptor;

    @Test
    void whenDoesNotSupportHtml_expectTextOnlyEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "Hey, let'use ArgumentCaptor";

        emailService.send(to, subject, body, false);

        verify(platform).deliver(emailCaptor.capture());
        Email emailCaptorValue = emailCaptor.getValue();
        assertThat(emailCaptorValue.getFormat()).isEqualTo(Format.TEXT_ONLY);
    }

    @Test
    void whenDoesSupportHtml_expectHTMLEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "<html><body>Hey, let'use ArgumentCaptor</body></html>";

        emailService.send(to, subject, body, true);

        verify(platform).deliver(emailCaptor.capture());
        Email value = emailCaptor.getValue();
        assertThat(value.getFormat()).isEqualTo(Format.HTML);
    }

    @Test
    void whenServiceRunning_expectUpResponse() {
        when(platform.getServiceStatus()).thenReturn("OK");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertThat(serviceStatus).isEqualTo(ServiceStatus.UP);
    }

    @Test
    void whenServiceNotRunning_expectDownResponse() {
        when(platform.getServiceStatus()).thenReturn("Error");

        ServiceStatus serviceStatus = emailService.checkServiceStatus();

        assertThat(serviceStatus).isEqualTo(ServiceStatus.DOWN);
    }

    @Test
    void whenUsingArgumentMatcherForValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        when(platform.authenticate(eq(credentials))).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
    }

    @Test
    void whenUsingArgumentCaptorForValidCredentials_expectTrue() {
        Credentials credentials = new Credentials("baeldung", "correct_password", "correct_key");
        when(platform.authenticate(credentialsCaptor.capture())).thenReturn(AuthenticationStatus.AUTHENTICATED);

        assertTrue(emailService.authenticatedSuccessfully(credentials));
        assertThat(credentialsCaptor.getValue()).isEqualTo(credentials);
    }

    @Test
    void whenNotAuthenticated_expectFalse() {
        Credentials credentials = new Credentials("baeldung", "incorrect_password", "incorrect_key");
        when(platform.authenticate(eq(credentials))).thenReturn(AuthenticationStatus.NOT_AUTHENTICATED);

        assertFalse(emailService.authenticatedSuccessfully(credentials));
    }
}