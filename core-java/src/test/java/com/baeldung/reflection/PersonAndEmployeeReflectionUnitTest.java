package com.baeldung.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        Field lastNameField = allFields[0];
        assertEquals(LAST_NAME_FIELD, lastNameField.getName());
        assertEquals(String.class, lastNameField.getType());

        Field firstNameField = allFields[1];
        assertEquals(FIRST_NAME_FIELD, firstNameField.getName());
        assertEquals(String.class, firstNameField.getType());
    }

    @Test
    public void givenEmployeeClass_whenSuperClassGetDeclaredFields_thenOneField() {
        // When
        Field[] allFields = Employee.class.getSuperclass().getDeclaredFields();

        // Then
        assertEquals(2, allFields.length);

        Field lastNameField = allFields[0];
        assertEquals(LAST_NAME_FIELD, lastNameField.getName());
        assertEquals(String.class, lastNameField.getType());

        Field firstNameField = allFields[1];
        assertEquals(FIRST_NAME_FIELD, firstNameField.getName());
        assertEquals(String.class, firstNameField.getType());
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFields_thenOneField() {
        // When
        Field[] allFields = Employee.class.getDeclaredFields();

        // Then
        assertEquals(1, allFields.length);

        Field employeeIdField = allFields[0];
        assertEquals(EMPLOYEE_ID_FIELD, employeeIdField.getName());
        assertEquals(int.class, employeeIdField.getType());
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

        Field lastNameField = allFields[0];
        assertEquals(LAST_NAME_FIELD, lastNameField.getName());
        assertEquals(String.class, lastNameField.getType());

        Field firstNameField = allFields[1];
        assertEquals(FIRST_NAME_FIELD, firstNameField.getName());
        assertEquals(String.class, firstNameField.getType());

        Field employeeIdField = allFields[2];
        assertEquals(EMPLOYEE_ID_FIELD, employeeIdField.getName());
        assertEquals(int.class, employeeIdField.getType());
    }

    @Test
    public void givenMonthEmployeeClass_whenGetAllFields_thenFourFields() {
        // When
        List<Field> allFields = getAllFields(MonthEmployee.class);

        // Then
        assertEquals(4, allFields.size());

        Field lastNameField = allFields.get(0);
        assertEquals(LAST_NAME_FIELD, lastNameField.getName());
        assertEquals(String.class, lastNameField.getType());

        Field firstNameField = allFields.get(1);
        assertEquals(FIRST_NAME_FIELD, firstNameField.getName());
        assertEquals(String.class, firstNameField.getType());

        Field employeeIdField = allFields.get(2);
        assertEquals(EMPLOYEE_ID_FIELD, employeeIdField.getName());
        assertEquals(int.class, employeeIdField.getType());

        Field monthEmployeeRewardField = allFields.get(3);
        assertEquals(MONTH_EMPLOYEE_REWARD_FIELD, monthEmployeeRewardField.getName());
        assertEquals(double.class, monthEmployeeRewardField.getType());
    }

    public List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>();
        result.addAll(getAllFields(clazz.getSuperclass()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return result;
    }

}
