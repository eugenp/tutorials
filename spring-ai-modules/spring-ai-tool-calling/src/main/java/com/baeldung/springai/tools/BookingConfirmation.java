package com.baeldung.springai.tools;

import java.time.LocalDate;

public record BookingConfirmation(String reference, String flightNumber, LocalDate date) {
}
