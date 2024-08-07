package com.baeldung.ddd.hexagonal.app.ports.out;

import com.baeldung.ddd.hexagonal.app.domain.Rental;

public interface RentalRepository {
	Rental save(Rental rental);
    Rental findById(Long rentalId);
}
