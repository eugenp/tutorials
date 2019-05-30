package com.baeldung.hexagonal.jpa;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.persistence.PersonFactory;

/**
 * {@link PersonFactory} which creates new {@link Person} JPA entities.
 */
final class PersonEntityFactory implements PersonFactory {

    @Override
    public Person create(CreatePersonRequest request) {
        return new PersonEntity(request.getFirst(), request.getLast());
    }
}
