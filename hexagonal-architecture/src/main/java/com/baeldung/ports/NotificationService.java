package com.baeldung.ports;

import com.baeldung.domain.Booking;

public interface NotificationService {
    void notify(Booking booking);
}
