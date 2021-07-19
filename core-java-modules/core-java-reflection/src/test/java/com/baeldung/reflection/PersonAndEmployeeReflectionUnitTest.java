package com.baeldung.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class PersonAndEmployeeReflectionUnitTest {

    // Fields names
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String EMPLOYEE_ID_FIELD = "employeeId";
    private static final String MONTH_EMPLOYEE_REWARD_FIELD = "reward";

    @Test
    public void givenPersonClass_whenGetDeclaredFields_thenTwoFields() {
        // When
        Field[] allFields = Person.class.getDeclaredFields();

        // Then
        assertEquals(2, allFields.length);

        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(LAST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(FIRST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFields_thenOneField() {
        // When
        Field[] allFields = Employee.class.getDeclaredFields();

        // Then
        assertEquals(1, allFields.length);

        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(EMPLOYEE_ID_FIELD)
            && field.getType().equals(int.class))
        );
    }

    @Test
    public void givenEmployeeClass_whenSuperClassGetDeclaredFields_thenOneField() {
        // When
        Field[] allFields = Employee.class.getSuperclass().getDeclaredFields();

        // Then
        assertEquals(2, allFields.length);

        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(LAST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(FIRST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFieldsOnBothClasses_thenThreeFields() {
        // When
        Field[] personFields = Employee.class.getSuperclass().getDeclaredFields();
        Field[] employeeFields = Employee.class.getDeclaredFields();
        Field[] allFields = new Field[employeeFields.length + personFields.length];
        Arrays.setAll(allFields, i -> (i < personFields.length ? personFields[i] : employeeFields[i - personFields.length]));

        // Then
        assertEquals(3, allFields.length);

        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(LAST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(FIRST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
        assertTrue(Arrays.stream(allFields).anyMatch(field ->
          field.getName().equals(EMPLOYEE_ID_FIELD)
            && field.getType().equals(int.class))
        );
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFieldsOnEmployeeSuperclassWithModifiersFilter_thenOneFields() {
        // When
        List<Field> personFields = Arrays.stream(Employee.class.getSuperclass().getDeclaredFields())
          .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers()))
          .collect(Collectors.toList());

        // Then
        assertEquals(1, personFields.size());

        assertTrue(personFields.stream().anyMatch(field ->
          field.getName().equals(LAST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
    }

    @Test
    public void givenMonthEmployeeClass_whenGetAllFields_thenThreeFields() {
        // When
        List<Field> allFields = getAllFields(MonthEmployee.class);

        // Then
        assertEquals(3, allFields.size());

        assertTrue(allFields.stream().anyMatch(field ->
          field.getName().equals(LAST_NAME_FIELD)
            && field.getType().equals(String.class))
        );
        assertTrue(allFields.stream().anyMatch(field ->
          field.getName().equals(EMPLOYEE_ID_FIELD)
            && field.getType().equals(int.class))
        );
        assertTrue(allFields.stream().anyMatch(field ->
          field.getName().equals(MONTH_EMPLOYEE_REWARD_FIELD)
            && field.getType().equals(double.class))
        );
    }

    public List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields())
          .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers()))
          .collect(Collectors.toList());
        result.addAll(filteredFields);
        return result;
    }

}
