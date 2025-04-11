package com.baeldung.mapstruct.emptystringtonull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MapEmptyStringToNullUnitTest {

    @Test
    void givenAMapperWithGlobalNullHandling_whenConvertingEmptyString_thenOutputNull() {
        EmptyStringToNullGlobal globalMapper = EmptyStringToNullGlobal.INSTANCE;
        Student student = new Student("Steve", "");
        Teacher teacher = globalMapper.toTeacher(student);
        assertEquals("Steve", teacher.firstName);
        assertNull(teacher.lastName);
    }

    @Test
    void givenAMapperWithConditionAnnotationNullHandling_whenConvertingEmptyString_thenOutputNull() {
        EmptyStringToNullCondition conditionMapper = EmptyStringToNullCondition.INSTANCE;
        Student student = new Student("Steve", "");
        Teacher teacher = conditionMapper.toTeacher(student);
        assertEquals("Steve", teacher.firstName);
        assertNull(teacher.lastName);
    }

    @Test
    void givenAMapperUsingExpressionBasedNullHandling_whenConvertingEmptyString_thenOutputNull() {
        EmptyStringToNullExpression expressionMapper = EmptyStringToNullExpression.INSTANCE;
        Student student = new Student("Steve", "");
        Teacher teacher = expressionMapper.toTeacher(student);
        assertEquals("Steve", teacher.firstName);
        assertNull(teacher.lastName);
    }

}
