package com.example.demo.service;

import com.example.demo.mapper.PersonMapper;
import com.example.demo.model.Person;
import com.example.demo.dto.PersonDto;
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
