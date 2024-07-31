package com.baeldung.fieldinjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceUnitTest {

    private EmailValidator emailValidator;

    private EmailService emailService;

    @BeforeEach
    public void setup() {
        this.emailValidator = Mockito.mock(EmailValidator.class);
        this.emailService = new EmailService(emailValidator);
    }

    @Test
    void givenInvalidEmail_whenProcess_thenThrowException() {
        String email = "testbaeldung.com";

        when(emailValidator.isValid(email)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> emailService.process(email));

        assertNotNull(exception);
        assertEquals(EmailService.INVALID_EMAIL, exception.getMessage());
    }
}
