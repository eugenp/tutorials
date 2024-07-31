package com.baeldung.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class PersonAndEmployeeReflectionUnitTest {

    private static final String LAST_NAME_FIELD = "lastName";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String EMPLOYEE_ID_FIELD = "employeeId";
    private static final String EMPLOYEE_TYPE_FIELD = "LABEL";
    private static final String MONTH_EMPLOYEE_REWARD_FIELD = "reward";

    @Test
    public void givenPersonClass_whenGetDeclaredFields_thenTwoFields() {
        List<Field> allFields = Arrays.asList(Person.class.getDeclaredFields());

        assertEquals(2, allFields.size());
        Field lastName = allFields.stream()
            .filter(field -> field.getName()
                .equals(LAST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, lastName.getType());
        Field firstName = allFields.stream()
            .filter(field -> field.getName()
                .equals(FIRST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, firstName.getType());
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFields_thenFilterAndReturnStaticField() {
        List<Field> publicStaticField = Arrays.stream(Employee.class.getDeclaredFields())
            .filter(field -> Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
            .collect(Collectors.toList());

        assertEquals(1, publicStaticField.size());
        Field employeeTypeField = publicStaticField.get(0);
        assertEquals(EMPLOYEE_TYPE_FIELD, employeeTypeField.getName());
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFields_thenTwoField() {
        List<Field> allFields = Arrays.asList(Employee.class.getDeclaredFields());

        assertEquals(2, allFields.size());
        Field employeeIdField = allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_ID_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(int.class, employeeIdField.getType());
        Field employeeTypeField = allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_TYPE_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, employeeTypeField.getType());
    }

    @Test
    public void givenEmployeeClass_whenSuperClassGetDeclaredFields_thenTwoField() {
        List<Field> allFields = Arrays.asList(Employee.class.getSuperclass()
            .getDeclaredFields());

        assertEquals(2, allFields.size());
        Field lastNameField = allFields.stream()
            .filter(field -> field.getName()
                .equals(LAST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, lastNameField.getType());
        Field firstNameField = allFields.stream()
            .filter(field -> field.getName()
                .equals(FIRST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, firstNameField.getType());
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFieldsOnBothClasses_thenFourFields() {
        List<Field> personFields = Arrays.asList(Employee.class.getSuperclass()
            .getDeclaredFields());
        List<Field> employeeFields = Arrays.asList(Employee.class.getDeclaredFields());
        List<Field> allFields = Stream.concat(personFields.stream(), employeeFields.stream())
            .collect(Collectors.toList());

        assertEquals(4, allFields.size());
        Field lastNameField = allFields.stream()
            .filter(field -> field.getName()
                .equals(LAST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, lastNameField.getType());
        Field firstNameField = allFields.stream()
            .filter(field -> field.getName()
                .equals(FIRST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, firstNameField.getType());
        Field employeeIdField = allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_ID_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(int.class, employeeIdField.getType());
        Field employeeTypeField = allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_TYPE_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, employeeTypeField.getType());
    }

    @Test
    public void givenEmployeeClass_whenGetDeclaredFieldsOnEmployeeSuperclassWithModifiersFilter_thenOneFields() {
        List<Field> personFields = Arrays.stream(Employee.class.getSuperclass()
                .getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers()))
            .collect(Collectors.toList());

        assertEquals(1, personFields.size());
        Field personField = personFields.stream()
            .filter(field -> field.getName()
                .equals(LAST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"));
        assertEquals(String.class, personField.getType());
    }

    @Test
    public void givenMonthEmployeeClass_whenGetAllFields_thenFourFields() {
        List<Field> allFields = getAllFields(MonthEmployee.class);

        assertEquals(4, allFields.size());
        assertFalse(allFields.stream()
            .anyMatch(field -> field.getName()
                .equals(FIRST_NAME_FIELD)));
        assertEquals(String.class, allFields.stream()
            .filter(field -> field.getName()
                .equals(LAST_NAME_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"))
            .getType());
        assertEquals(int.class, allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_ID_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"))
            .getType());
        assertEquals(double.class, allFields.stream()
            .filter(field -> field.getName()
                .equals(MONTH_EMPLOYEE_REWARD_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"))
            .getType());
        assertEquals(String.class, allFields.stream()
            .filter(field -> field.getName()
                .equals(EMPLOYEE_TYPE_FIELD))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Field not found"))
            .getType());
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
