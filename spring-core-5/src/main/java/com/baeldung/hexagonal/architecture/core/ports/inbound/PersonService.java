package com.baeldung.hexagonal.architecture.core.ports.inbound;

import com.baeldung.hexagonal.architecture.core.domain.Person;

public interface PersonService {
    Person findById(Long id);
}
