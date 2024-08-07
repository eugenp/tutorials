package com.baeldung.ddd.hexagonal.app.adapters.out.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.ddd.hexagonal.app.domain.Rental;
import com.baeldung.ddd.hexagonal.app.ports.out.RentalRepository;

@Repository
public class RentalRepositoryImpl implements RentalRepository {
	@Autowired
    private JpaRentalRepository jpaRentalRepository;

    @Override
    public Rental save(Rental rental) {
        return jpaRentalRepository.save(rental);
    }

    @Override
    public Rental findById(Long rentalId) {
        return jpaRentalRepository.findById(rentalId).orElse(null);
    }

}
