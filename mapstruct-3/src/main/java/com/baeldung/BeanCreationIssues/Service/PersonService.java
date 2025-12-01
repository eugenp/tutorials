package com.example.Service;

import com.example.Mapper.PersonMapper;
import com.example.Model.Person;
import com.example.Model.PersonDto;
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


