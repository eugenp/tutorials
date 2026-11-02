package com.baeldung.springai.tools;

import java.time.LocalDate;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.baeldung.springai.service.BookingService;

@Component
public class BookingTools {

    private final BookingService bookingService;

    public BookingTools(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Tool(description = "Book a one-way flight")
    public BookingConfirmation bookFlight(
        @ToolParam(description = "IATA code of the departure airport") String origin,
        @ToolParam(description = "IATA code of the destination airport") String destination,
        @ToolParam(description = "Travel date in ISO-8601 format") LocalDate date) {
        return bookingService.book(origin, destination, date);
    }
}
