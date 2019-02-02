package com.baeldung.springboot.service;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;
import com.baeldung.springboot.respository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {

        this.personRepository = personRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public PersonResponse createPerson(PersonDto personDto) {

        Person personEntity = modelMapper.map(personDto, Person.class);

        Person person = personRepository.save(personEntity);

        return new PersonResponse("Registration Success.", String.valueOf(person.getId()));
    }

}
