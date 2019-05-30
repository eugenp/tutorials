package com.baeldung.hexagonal.fake;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.persistence.PersonFactory;
import java.util.concurrent.atomic.AtomicInteger;

class FakePersonFactory implements PersonFactory {

    private final AtomicInteger id = new AtomicInteger();

    @Override
    public Person create(CreatePersonRequest request) {
        return new FakePerson(id.getAndIncrement(), request.getFirst(), request.getLast());
    }
}
