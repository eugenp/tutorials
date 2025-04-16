package com.baeldung.mapstruct.emptystringtonull;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.SourcePropertyName;
import org.mapstruct.TargetPropertyName;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmptyStringToNullConditionSourceProperty {

    EmptyStringToNullConditionSourceProperty INSTANCE = Mappers.getMapper(EmptyStringToNullConditionSourceProperty.class);

    Teacher toTeacher(Student student);

    @Condition
    default boolean isNotEmpty(String value, @TargetPropertyName String targetPropertyName, @SourcePropertyName String sourcePropertyName) {
        if( sourcePropertyName.equals("lastName")) {
            return value != null && !value.isEmpty();
        }
        return true;
    }
}
