package com.baeldung.exceptionhandling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@SpringBootTest
public class ExceptionHandlingAspectTest {

    @Autowired
    private PaymentService paymentService;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(ExceptionHandlingAspect.class);

        listAppender = new ListAppender<>();
        listAppender.start();

        logger.addAppender(listAppender);
    }

    @Test
    public void givenInvalidPaymentDetails_whenValidationFails_thenAspectLogsException() {
        // When
        assertThatThrownBy(() -> paymentService.validatePaymentDetails("")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Payment details cannot be null or empty.");

        // Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).extracting(Object::toString)
            .containsExactly("[ERROR] Exception occurred: Payment details cannot be null or empty.");
    }

    @Test
    public void givenInvalidPaymentDetails_whenProcessingFails_thenAspectLogsException() {
        // When
        assertThatThrownBy(() -> paymentService.processPayment("INVALID")).isInstanceOf(RuntimeException.class)
            .hasMessage("Payment processing failed due to invalid details.");

        // Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).extracting(Object::toString)
            .containsExactly("[ERROR] Exception occurred: Payment processing failed due to invalid details.");
    }
}
