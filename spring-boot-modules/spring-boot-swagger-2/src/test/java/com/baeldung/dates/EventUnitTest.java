package com.baeldung.dates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import io.swagger.model.Event;

class EventUnitTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    void givenACorrectlyFormattedTicketSales_WhenBuildingEvent_ThenSuccess() {
        Set<ConstraintViolation<Event>> violations = VALIDATOR.validate(new Event().ticketSales("01-01-2024"));
        assertTrue(violations.isEmpty());
    }

    @Test
    void givenAWronglyFormattedTicketSales_WhenBuildingEvent_ThenSuccess() {
        Set<ConstraintViolation<Event>> violations = VALIDATOR.validate(new Event().ticketSales("2024-01-01"));
        assertEquals(1, violations.size());
    }

}
