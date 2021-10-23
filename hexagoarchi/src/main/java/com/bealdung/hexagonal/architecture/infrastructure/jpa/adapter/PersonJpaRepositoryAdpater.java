package com.bealdung.hexagonal.architecture.infrastructure.jpa.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bealdung.hexagonal.architecture.domain.model.Person;
import com.bealdung.hexagonal.architecture.domain.repository.PersonRepositoryPort;
import com.bealdung.hexagonal.architecture.infrastructure.jpa.entity.PersonEntity;
import com.bealdung.hexagonal.architecture.infrastructure.jpa.repository.PersonRepository;

public class PersonJpaRepositoryAdpater implements PersonRepositoryPort {

    private PersonRepository personRepository;

    public PersonJpaRepositoryAdpater(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll()
            .stream()
            .map(PersonEntity::toPerson)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id)
            .map(PersonEntity::toPerson);
    }

    @Override
    public Person save(Person std) {
        return personRepository.save(PersonEntity.fromPerson(std))
            .toPerson();
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }

}
