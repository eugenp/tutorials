package com.baeldung.mapstructstringmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "age", expression = "java(String.valueOf(person.getAge()))")
    PersonDTO toDTO(Person person);
}
