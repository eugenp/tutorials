package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.BookingServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.baeldung.hexagonal.port.BookingServicePort.BookingRequest;
import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse;
import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse.*;

@RestController
public class RestAPIEndpointAdapter {

    private BookingServicePort bookingServicePort;

    @Autowired
    public RestAPIEndpointAdapter(BookingServicePort bookingServicePort) {
        this.bookingServicePort = bookingServicePort;
    }

    @PostMapping(path = "/booking")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse response = bookingServicePort.book(request);

        if (response.getStatusCode() == SEAT_NOT_AVAILABLE
                    || response.getStatusCode() == PAYMENT_FAILED){
            return new ResponseEntity<BookingResponse>(response, HttpStatus.PRECONDITION_FAILED);
        } else if (response.getStatusCode() == UNKNOWN_ERROR) {
            return new ResponseEntity<BookingResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BookingResponse>(response, HttpStatus.CREATED);
    }
}
