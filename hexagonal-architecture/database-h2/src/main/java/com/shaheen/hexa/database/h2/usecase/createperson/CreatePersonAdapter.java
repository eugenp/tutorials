package com.shaheen.hexa.database.h2.usecase.createperson;

import com.shaheen.hexa.core.domain.Person;
import com.shaheen.hexa.core.usecase.createperson.CreatePersonPort;
import com.shaheen.hexa.database.h2.domain.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreatePersonAdapter implements CreatePersonPort {
    private final CreatePersonRepository createPersonH2Repo;

    @Autowired
    public CreatePersonAdapter(final CreatePersonRepository createPersonRepository) {
        this.createPersonH2Repo = createPersonRepository;
    }

    @Override
    public Long createPerson(final Person person) {
        final PersonEntity personEntity = new PersonEntity(person.getName(), person.getFirstName(), person.getAge());
        log.info("[H2] Create person: {}", personEntity);
        return createPersonH2Repo.save(personEntity).getId();
    }
}
