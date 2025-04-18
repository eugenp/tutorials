package com.baeldung.mapstruct.emptystringtonull;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmptyStringToNullGlobal {

    EmptyStringToNullGlobal INSTANCE = Mappers.getMapper(EmptyStringToNullGlobal.class);

    Teacher toTeacher(Student student);

    default String mapEmptyString(String string) {
        return string != null && !string.isEmpty() ? string : null;
    }
}
