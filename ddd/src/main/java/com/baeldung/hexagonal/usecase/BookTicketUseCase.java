package com.baeldung.hexagonal.usecase;

import com.baeldung.hexagonal.domain.Booking;
import com.baeldung.hexagonal.port.BookingPersistencePort;
import com.baeldung.hexagonal.port.BookingServicePort;
import com.baeldung.hexagonal.port.TheatreServicePort;
import com.baeldung.hexagonal.port.WalletServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.baeldung.hexagonal.domain.Booking.STATUS_FAILURE;
import static com.baeldung.hexagonal.domain.Booking.STATUS_SUCCESS;
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

        Booking booking = new Booking(request);

        if (!bookingPersistencePort.persist(booking)) {
            return new BookingResponse(UNKNOWN_ERROR);
        }

        String bookingId = booking.getBookingId();

        Optional<String> reservationIdOptional = theatreServicePort.reserveSeats(booking.getMovieShowId(), booking.getSeats());
        if (!reservationIdOptional.isPresent()) {
            bookingPersistencePort.updateStatus(bookingId, STATUS_FAILURE);
            return new BookingResponse(bookingId, SEAT_NOT_AVAILABLE);
        }

        if (!walletServicePort.debit(booking.getCustomerId(), booking.getAmount())) {
            reservationIdOptional.ifPresent(reservationId -> theatreServicePort.releaseSeats(reservationId));
            bookingPersistencePort.updateStatus(bookingId, STATUS_FAILURE);
            return new BookingResponse(bookingId, PAYMENT_FAILED);
        }

        bookingPersistencePort.updateStatus(bookingId, STATUS_SUCCESS);

        return new BookingResponse(bookingId, SUCCESS);
    }
}
