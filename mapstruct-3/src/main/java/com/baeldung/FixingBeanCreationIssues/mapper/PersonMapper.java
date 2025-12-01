package com.baeldung.fixingbeancreationissues.mapper;

import org.mapstruct.Mapper;
import com.baeldung.fixingbeancreationissues.model.Person;
import com.baeldung.fixingbeancreationissues.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto dto);
}
