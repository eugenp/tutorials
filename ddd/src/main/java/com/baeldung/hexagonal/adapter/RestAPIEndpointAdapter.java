package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.BookingServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.baeldung.hexagonal.port.BookingServicePort.BookingRequest;
import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse;
import static com.baeldung.hexagonal.port.BookingServicePort.BookingResponse.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FAILED_DEPENDENCY;

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
        return new ResponseEntity<>(response,
                response.getStatusCode() == SUCCESS ? CREATED : FAILED_DEPENDENCY);
    }
}
