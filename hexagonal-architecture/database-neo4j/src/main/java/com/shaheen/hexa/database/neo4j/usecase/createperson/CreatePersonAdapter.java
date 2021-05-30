package main.java.com.shaheen.hexa.database.neo4j.usecase.createperson;

import com.shaheen.hexa.core.domain.Person;
import com.shaheen.hexa.core.usecase.createperson.CreatePersonPort;
import main.java.com.shaheen.hexa.database.neo4j.domain.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreatePersonAdapter implements CreatePersonPort {
    private final CreatePersonRepository createPersonNeo4JRepository;

    @Autowired
    public CreatePersonAdapter(final CreatePersonRepository createPersonRepository) {
        this.createPersonNeo4JRepository = createPersonRepository;
    }

    @Override
    public Long createPerson(final Person person) {
        final PersonEntity personEntity = new PersonEntity(person.getName(), person.getFirstName(), person.getAge());
        return createPersonNeo4JRepository.save(personEntity).getId();
    }
}
