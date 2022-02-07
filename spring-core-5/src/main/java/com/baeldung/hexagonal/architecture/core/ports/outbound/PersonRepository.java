package com.baeldung.hexagonal.architecture.core.ports.outbound;

import com.baeldung.hexagonal.architecture.core.domain.Person;

public interface PersonRepository {
    Person findById(Long id);
}
