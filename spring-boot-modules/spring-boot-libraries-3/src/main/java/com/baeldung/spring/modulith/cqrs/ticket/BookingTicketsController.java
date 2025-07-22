package com.baeldung.spring.modulith.cqrs.ticket;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.modulith.cqrs.ticket.domain.BookedTicketsCommandHandler;

@RestController
@RequestMapping("api/ticket-booking")
public class BookingTicketsController {

    private final BookedTicketsCommandHandler bookedTicketsCommandHandler;

    BookingTicketsController(BookedTicketsCommandHandler bookedTicketService) {
        this.bookedTicketsCommandHandler = bookedTicketService;
    }

    /*
       curl -X POST http://localhost:8080/api/ticket-booking ^
         -H "Content-Type: application/json" ^
         -d "{\"id\": 1, \"seat\": \"A1\"}"
     */
    @PostMapping
    BookingResponse bookTicket(@RequestBody BookTicket request) {
        long id = bookedTicketsCommandHandler.bookTicket(request);
        return new BookingResponse(id);
    }

    public record BookingResponse(Long bookingId) {

    }

    /*
      curl -X DELETE http://localhost:8080/api/ticket-booking/1
    */
    @DeleteMapping("/{movieId}")
    CancellationResponse cancelBooking(@PathVariable Long movieId) {
        long id = bookedTicketsCommandHandler.cancelTicket(new CancelTicket(movieId));
        return new CancellationResponse(id);
    }

    public record CancellationResponse(Long cancellationId) {

    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage()));
    }

    public record ErrorResponse(String error) {

    }

}
