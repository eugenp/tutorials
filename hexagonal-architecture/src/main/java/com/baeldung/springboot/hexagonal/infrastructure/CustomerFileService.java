package com.baeldung.springboot.hexagonal.infrastructure;

import java.util.List;
import java.util.UUID;

import com.baeldung.springboot.hexagonal.domain.Reservation;

public interface CustomerFileService {
    public Reservation save(Reservation booking);

    public List<Reservation> bookings(Long customerId);

    public boolean delete(Long customerId, UUID booking);
}
