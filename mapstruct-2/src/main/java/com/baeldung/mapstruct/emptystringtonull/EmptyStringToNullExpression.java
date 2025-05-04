package com.baeldung.mapstruct.emptystringtonull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmptyStringToNullExpression {
    EmptyStringToNullExpression INSTANCE = Mappers.getMapper(EmptyStringToNullExpression.class);

    @Mapping(target = "lastName", expression = "java(student.lastName.isEmpty() ? null : student.lastName)")
    Teacher toTeacher(Student student);
}
