package com.baeldung.domain;

import com.baeldung.ports.BookingRequest;
import com.baeldung.ports.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import static com.baeldung.domain.Booking.Status.CONFIRMED;
import static com.baeldung.domain.Booking.Status.PENDING;

public class Booking {

    @Autowired
    NotificationService notificationService;

    private String email;

    private Status status;

    public Booking(BookingRequest request) {
        this.email = request.getEmail();
        this.status = PENDING;
    }

    public void confirm() {
        this.status = CONFIRMED;
        notificationService.notify(this);
    }

    public String getEmail() {
        return email;
    }

    enum Status {
        PENDING, CONFIRMED
    }
}
