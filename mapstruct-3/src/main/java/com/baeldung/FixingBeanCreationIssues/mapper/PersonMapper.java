package com.example.demo.mapper;

import org.mapstruct.Mapper;
import com.example.demo.model.Person;
import com.example.demo.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto dto);
}
