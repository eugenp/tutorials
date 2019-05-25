package com.baeldung.hexagonal.domain;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepositoryPort {

    void save(Person person);

    Optional<Person> findById(UUID uuid);
}
