package com.baeldung.fixingbeancreationissues.service;
import com.baeldung.fixingbeancreationissues.dto.PersonDto;
import com.baeldung.fixingbeancreationissues.mapper.PersonMapper;       
import com.baeldung.fixingbeancreationissues.model.Person;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonMapper personMapper;

    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    public PersonDto convertToDto(Person person) {
        return personMapper.toDto(person);
    }

    public Person convertToEntity(PersonDto dto) {
        return personMapper.toEntity(dto);
    }
}
