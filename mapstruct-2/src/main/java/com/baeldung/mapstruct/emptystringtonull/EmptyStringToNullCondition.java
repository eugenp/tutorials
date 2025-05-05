package com.baeldung.mapstruct.emptystringtonull;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmptyStringToNullCondition {

    EmptyStringToNullCondition INSTANCE = Mappers.getMapper(EmptyStringToNullCondition.class);

    Teacher toTeacher(Student student);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
