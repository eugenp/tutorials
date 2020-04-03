package com.baeldung.hexagonarch.adapters.driven;

import org.hexagonarch.ports.driven.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SpringJpaPersonRepository extends JpaRepository<Person, Integer> {
    public Optional<Collection<Person>> findByStreetNameAndCityAndZipCode(String streetName, String city, String zipCode);
}
