package org.java.spring.hexagonalarchitecture.inside;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepositoryPort personRepositoryPort;

    @Autowired
    @Qualifier("SMSCommunicationAdapter")
    CommunicationPort smsCommumicationPort;

    @Autowired
    @Qualifier("EmailCommunicationAdapter")
    CommunicationPort emailCommumicationPort;

    public List<Person> findAll() {
        return personRepositoryPort.findAll();
    }

    public Person save(Person person) {
        Person savedPerson = personRepositoryPort.save(person);
        smsCommumicationPort.sendMessage("SMS", "Person Details updated", savedPerson);
        emailCommumicationPort.sendMessage("EMAIL", "Person Details updated", savedPerson);
        return savedPerson;
    }

    public Person findById(Long id) {
        Optional<Person> person = personRepositoryPort.findById(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            return null;
        }

    }

}
