package com.baeldung.fixingbeancreationissues.mapper;

import org.mapstruct.Mapper;
import com.baeldung.fixingbeancreationissues.model.Person;
import com.baeldung.fixingbeancreationissues.dto.PersonDto;

// @Mapper {Incorrect}
//@Mapper(componentModel = "spring") {Correct}
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto dto);
}
