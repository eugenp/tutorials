package com.baeldung.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.PersonDTO;
import com.baeldung.entity.Person;

@Mapper
public interface PersonMapper {
    
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    
    @Mapping(target = "id", source = "person.id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "name", source = "person.name", defaultValue = "anonymous")
    PersonDTO personToPersonDTO(Person person);
}
