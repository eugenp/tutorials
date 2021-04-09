package com.baeldung.hexagonal.usecase;

import com.baeldung.hexagonal.domain.Booking;
import com.baeldung.hexagonal.port.BookingPersistencePort;
import com.baeldung.hexagonal.port.BookingServicePort;
import com.baeldung.hexagonal.port.TheatreServicePort;
import com.baeldung.hexagonal.port.WalletServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookTicketUseCaseUnitTest {

    private BookingPersistencePort bookingPersistencePort;
    private TheatreServicePort theatreServicePort;
    private WalletServicePort walletServicePort;
    private BookTicketUseCase bookTicketUseCase;

    @BeforeEach
    void setUp() {
        bookingPersistencePort = mock(BookingPersistencePort.class);
        theatreServicePort = mock(TheatreServicePort.class);
        walletServicePort = mock(WalletServicePort.class);
        bookTicketUseCase = new BookTicketUseCase(bookingPersistencePort, theatreServicePort, walletServicePort);
    }

    private BookingServicePort.BookingRequest getBookingRequest() {
        BookingServicePort.BookingRequest request = new BookingServicePort.BookingRequest();
        request.setMovieShowId("movie-show-id");
        request.setCustomerId("customer-id");
        request.setSeats(new HashSet<>(Arrays.asList("A1", "A2")));
        request.setAmount(100.00);
        return request;
    }

    @Test
    void whenErrorInInitialPersistence_thenReturnUnknownError() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        Booking booking = new Booking(request);
        when(bookingPersistencePort.persist(any(Booking.class))).thenReturn(false);
        BookingServicePort.BookingResponse response = bookTicketUseCase.book(request);

        verify(bookingPersistencePort, times(1)).persist(any(Booking.class));
        assertNotNull(response);
        assertEquals(BookingServicePort.BookingResponse.UNKNOWN_ERROR, response.getStatusCode());
    }

    @Test
    void whenErrorInReserveSeats_thenReturnSeatNotAvailable() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        Booking booking = new Booking(request);
        when(bookingPersistencePort.persist(any(Booking.class))).thenReturn(true);
        when(theatreServicePort.reserveSeats(booking.getMovieShowId(), booking.getSeats()))
                .thenReturn(Optional.empty());
        BookingServicePort.BookingResponse response = bookTicketUseCase.book(request);

        verify(bookingPersistencePort).persist(any(Booking.class));
        verify(bookingPersistencePort).updateStatus(any(String.class), any(String.class));
        verify(theatreServicePort).reserveSeats(any(String.class), any(HashSet.class));
        assertNotNull(response);
        assertEquals(BookingServicePort.BookingResponse.SEAT_NOT_AVAILABLE, response.getStatusCode());
    }

    @Test
    void whenErrorInWalletDebit_thenReturnPaymentFailed() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        Booking booking = new Booking(request);
        when(bookingPersistencePort.persist(any(Booking.class))).thenReturn(true);
        when(theatreServicePort.reserveSeats(booking.getMovieShowId(), booking.getSeats()))
                .thenReturn(Optional.of("reservation-id"));
        when(walletServicePort.debit(booking.getCustomerId(), booking.getAmount()))
                .thenReturn(false);
        BookingServicePort.BookingResponse response = bookTicketUseCase.book(request);

        verify(bookingPersistencePort).persist(any(Booking.class));
        verify(bookingPersistencePort).updateStatus(any(String.class), any(String.class));
        verify(theatreServicePort).reserveSeats(any(String.class), any(HashSet.class));
        verify(walletServicePort).debit(any(String.class), any(Double.class));
        verify(theatreServicePort).releaseSeats(any(String.class));
        assertNotNull(response);
        assertEquals(BookingServicePort.BookingResponse.PAYMENT_FAILED, response.getStatusCode());
    }

    @Test
    void whenNoErrorInAnyPorts_thenReturnSuccess() {
        BookingServicePort.BookingRequest request = getBookingRequest();
        Booking booking = new Booking(request);
        when(bookingPersistencePort.persist(any(Booking.class))).thenReturn(true);
        when(theatreServicePort.reserveSeats(booking.getMovieShowId(), booking.getSeats()))
                .thenReturn(Optional.of("reservation-id"));
        when(walletServicePort.debit(booking.getCustomerId(), booking.getAmount()))
                .thenReturn(true);
        BookingServicePort.BookingResponse response = bookTicketUseCase.book(request);

        verify(bookingPersistencePort).persist(any(Booking.class));
        verify(bookingPersistencePort).updateStatus(any(String.class), any(String.class));
        verify(theatreServicePort).reserveSeats( any(String.class), any(HashSet.class));
        verify(walletServicePort).debit(any(String.class), any(Double.class));
        assertNotNull(response);
        assertEquals(BookingServicePort.BookingResponse.SUCCESS, response.getStatusCode());
    }
}