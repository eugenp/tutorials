package com.baeldung.hexagonal.usecase;

import com.baeldung.hexagonal.domain.Booking;
import com.baeldung.hexagonal.port.BookingPersistencePort;
import com.baeldung.hexagonal.port.BookingServicePort;
import com.baeldung.hexagonal.port.TheatreServicePort;
import com.baeldung.hexagonal.port.WalletServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse.*;

@Component
public class BookTicketUseCase implements BookingServicePort {

    private BookingPersistencePort bookingPersistencePort;
    private TheatreServicePort theatreServicePort;
    private WalletServicePort walletServicePort;

    @Autowired
    public BookTicketUseCase(BookingPersistencePort bookingPersistencePort, TheatreServicePort theatreServicePort, WalletServicePort walletServicePort) {
        this.bookingPersistencePort = bookingPersistencePort;
        this.theatreServicePort = theatreServicePort;
        this.walletServicePort = walletServicePort;
    }

    public BookingResponse book(BookingRequest request) {

        Booking booking = new Booking(
                            UUID.randomUUID().toString(),
                            request.getMovieShowId(),
                            request.getTheatreId(),
                            request.getCustomerId(),
                            request.getSeats(),
                            request.getAmount(),
                            Booking.Status.INITIAL);

        if (!bookingPersistencePort.persist(booking)) {
            return new BookingResponse(UNKNOWN_ERROR);
        }

        Optional<String> reservationIdOptional = theatreServicePort.reserveSeats(
                booking.getTheatreId(),
                booking.getMovieShowId(),
                booking.getSeats());
        if (!reservationIdOptional.isPresent()) {
            booking.setStatus(Booking.Status.FAILURE);
            bookingPersistencePort.persist(booking);
            return new BookingResponse(SEAT_NOT_AVAILABLE);
        }

        if (!walletServicePort.debit(booking.getCustomerId(), booking.getAmount())) {
            reservationIdOptional.ifPresent(reservationId -> theatreServicePort.releaseSeats(reservationId));
            booking.setStatus(Booking.Status.FAILURE);
            bookingPersistencePort.persist(booking);
            return new BookingResponse(PAYMENT_FAILED);
        }

        booking.setStatus(Booking.Status.SUCCESS);
        bookingPersistencePort.persist(booking);

        return new BookingResponse(SUCCESS);
    }
}
