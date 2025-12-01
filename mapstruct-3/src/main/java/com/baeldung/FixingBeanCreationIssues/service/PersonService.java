package com.baeldung.FixingBeanCreationIssues.service;

import com.baeldung.FixingBeanCreationIssues.mapper.PersonMapper;
import com.baeldung.FixingBeanCreationIssues.model.Person;
import com.baeldung.FixingBeanCreationIssues.dto.PersonDto;
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
