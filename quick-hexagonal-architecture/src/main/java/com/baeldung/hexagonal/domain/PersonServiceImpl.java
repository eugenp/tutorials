package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.persistence.PersonFactory;
import com.baeldung.hexagonal.persistence.PersonRepository;
import javax.inject.Inject;

/**
 * Package-private implementation of the {@link PersonService} because modules outside this package should not know which concrete class implements {@link PersonService}.
 */
class PersonServiceImpl implements PersonService {

    private final PersonFactory factory;
    private final PersonRepository repository;

    @Inject
    PersonServiceImpl(PersonFactory factory, PersonRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public Person create(CreatePersonRequest request) {
        final Person person = factory.create(request);
        repository.save(person);
        return person;
    }

    @Override
    public Person get(int id) {
        return repository.getById(id);
    }
}
