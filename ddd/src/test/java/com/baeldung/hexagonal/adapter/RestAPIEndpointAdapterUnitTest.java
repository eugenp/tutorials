package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.BookingServicePort;
import com.baeldung.hexagonal.port.BookingServicePort.BookingRequest;
import com.baeldung.hexagonal.port.BookingServicePort.BookingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;

import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestAPIEndpointAdapterUnitTest {

    private BookingServicePort bookingServicePort;
    private RestAPIEndpointAdapter restAPIEndpointAdapter;

    @BeforeEach
    void setUp() {
        bookingServicePort = mock(BookingServicePort.class);
        restAPIEndpointAdapter = new RestAPIEndpointAdapter(bookingServicePort);
    }

    private BookingServicePort.BookingRequest getBookingRequest() {
        BookingServicePort.BookingRequest request = new BookingServicePort.BookingRequest();
        request.setTheatreId("theatre-id");
        request.setMovieShowId("movie-show-id");
        request.setCustomerId("customer-id");
        request.setSeats(new HashSet<>(Arrays.asList("A1", "A2")));
        request.setAmount(100.00);
        return request;
    }

    @Test
    void whenBookingServicePortReturnsUnknownError_thenReturnInternalServerError() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        when(bookingServicePort.book(any(BookingRequest.class))).thenReturn(new BookingResponse(UNKNOWN_ERROR));

        ResponseEntity<BookingResponse> response = restAPIEndpointAdapter.createBooking(request);

        verify(bookingServicePort).book(any(BookingRequest.class));
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void whenBookingServicePortReturnsSeatUnavailable_thenReturnPreconditionFailed() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        when(bookingServicePort.book(any(BookingRequest.class))).thenReturn(new BookingResponse(SEAT_NOT_AVAILABLE));

        ResponseEntity<BookingResponse> response = restAPIEndpointAdapter.createBooking(request);

        verify(bookingServicePort).book(any(BookingRequest.class));
        assertNotNull(response);
        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    void whenBookingServicePortReturnsPaymentFailed_thenReturnPreconditionFailed() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        when(bookingServicePort.book(any(BookingRequest.class))).thenReturn(new BookingResponse(PAYMENT_FAILED));

        ResponseEntity<BookingResponse> response = restAPIEndpointAdapter.createBooking(request);

        verify(bookingServicePort).book(any(BookingRequest.class));
        assertNotNull(response);
        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    void whenBookingServicePortReturnsSuccess_thenReturnCreated() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        when(bookingServicePort.book(any(BookingRequest.class))).thenReturn(new BookingResponse(SUCCESS));

        ResponseEntity<BookingResponse> response = restAPIEndpointAdapter.createBooking(request);

        verify(bookingServicePort).book(any(BookingRequest.class));
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}