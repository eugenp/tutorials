package com.baeldung.mapstructstringmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "name", qualifiedByName = "mapName")
    @Mapping(target = "age", expression = "java(String.valueOf(person.getAge()))")
    PersonDTO toDTO(Person person);

    @Named("mapName")
    default String mapName(String name) {
        return name == null ? "Unknown" : name;
    }
}
