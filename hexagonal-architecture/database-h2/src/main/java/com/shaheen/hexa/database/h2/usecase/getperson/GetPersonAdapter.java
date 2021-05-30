package com.shaheen.hexa.database.h2.usecase.getperson;

import com.shaheen.hexa.core.domain.Person;
import com.shaheen.hexa.core.usecase.getperson.GetPersonPort;
import com.shaheen.hexa.core.usecase.getperson.NotFoundException;
import com.shaheen.hexa.database.h2.domain.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetPersonAdapter implements GetPersonPort {
    private final GetPersonRepository getPersonRepository;

    @Autowired
    public GetPersonAdapter(final GetPersonRepository getPersonRepository) {
        this.getPersonRepository = getPersonRepository;
    }

    @Override
    public Person getPerson(final Long id) {
        final PersonEntity personEntity = getPersonRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Person with ID '%d' not found", id)));
        log.info("[H2] Get person: {}", personEntity);
        return new Person(personEntity.getId(), personEntity.getName(), personEntity.getFirstName(), personEntity.getAge());
    }
}
