package com.baeldung.spring.modulith.cqrs.ticket;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket-booking")
class BookingTicketsController {

    private final TicketBookingCommandHandler bookedTickets;

    BookingTicketsController(TicketBookingCommandHandler bookedTicketService) {
        this.bookedTickets = bookedTicketService;
    }

    /*
       curl -X POST http://localhost:8080/api/ticket-booking ^
         -H "Content-Type: application/json" ^
         -d "{\"id\": 1, \"seat\": \"A1\"}"
     */
    @PostMapping
    BookingResponse bookTicket(@RequestBody BookTicket request) {
        long id = bookedTickets.bookTicket(request);
        return new BookingResponse(id);
    }

    record BookingResponse(Long bookingId) {

    }

    /*
      curl -X DELETE http://localhost:8080/api/ticket-booking/1
    */
    @DeleteMapping("/{movieId}")
    CancellationResponse cancelBooking(@PathVariable Long movieId) {
        long id = bookedTickets.cancelTicket(new CancelTicket(movieId));
        return new CancellationResponse(id);
    }

    record CancellationResponse(Long cancellationId) {

    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage()));
    }

    public record ErrorResponse(String error) {

    }

}
