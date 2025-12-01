package com.baeldung.FixingBeanCreationIssues.mapper;

import org.mapstruct.Mapper;
import com.baeldung.FixingBeanCreationIssues.model.Person;
import com.baeldung.FixingBeanCreationIssues.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto dto);
}
