package com.baeldung.hexagonarch.core.service;

import com.baeldung.hexagonarch.core.model.PersonDomain;
import com.baeldung.hexagonarch.ports.driven.Person;
import com.baeldung.hexagonarch.ports.driven.PersonRepository;
import com.baeldung.hexagonarch.ports.driver.MailBoxService;

import java.util.Collection;
import java.util.Optional;

public class MailBoxServiceImpl implements MailBoxService {

    private PersonRepository personRepository;

    public MailBoxServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void sendFlyer(String streetName, String city, String zipCode) {
        Optional<Collection<Person>> userInNeighbourhood = personRepository.findPersonsInNeighbourhood(streetName, city, zipCode);
        userInNeighbourhood.ifPresent(persons -> persons.stream()
                .map(PersonDomain::fromPerson)
                .forEach(this::sendFlyerContent));
    }

    private void sendFlyerContent(PersonDomain person) {
        String letter = prepareLetterContent(person);
        System.out.println(letter);
    }

    private String prepareLetterContent(PersonDomain person) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hello ").append(person.getName());
        builder.append("\n");
        builder.append("Our brand new product is available in your ").append(person.getCity());
        builder.append("\n");
        builder.append("Cheers");

        return builder.toString();
    }
}
