package com.example.Mapper;

import com.example.Model.Person;
import com.example.Model.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
}


