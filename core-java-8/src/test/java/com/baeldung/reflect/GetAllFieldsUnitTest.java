package com.baeldung.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GetAllFieldsUnitTest {

    @Test
    public void getClassFieldsNoDeclared_thenError() {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(Alumn.class.getFields()));
        assertFalse(fieldList.stream()
            .anyMatch(field -> field.getName()
                .equals("uid")));
    }

    @Test
    public void getClassFields_thenOk() {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(Alumn.class.getFields()));
        assertTrue(fieldList.stream()
            .anyMatch(field -> field.getName()
                .equals("campus")));
    }

    @Test
    public void getClassFieldsNoParent_thenOk() {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(Alumn.class.getDeclaredFields()));
        assertFalse(fieldList.stream()
            .anyMatch(field -> field.getName()
                .equals("fullName")));
    }

    @Test
    public void getClassFieldsParentNoDeclared_thenError() {
        Class clazz = Alumn.class;
        List<Field> fieldList = new ArrayList<>();

        while (clazz != Object.class) {
            fieldList.addAll(Arrays.asList(clazz.getFields()));
            clazz = clazz.getSuperclass();
        }
        assertFalse(fieldList.stream()
            .anyMatch(field -> field.getName()
                .equals("fullName")));
    }

    @Test
    public void getClassFieldsParentDeclared_thenOk() {
        Class clazz = Alumn.class;
        List<Field> fieldList = new ArrayList<>();

        while (clazz != Object.class) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        assertTrue(fieldList.stream()
            .anyMatch(field -> field.getName()
                .equals("fullName")));
    }

    private List<Field> getAllDeclaredFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();

        while (clazz != Object.class) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    @Test
    public void readValue_thenOk() throws IllegalAccessException {
        Alumn alumn = new Alumn("Peter Sampras", "A-1", 1);
        List<Field> fieldList = getAllDeclaredFields(alumn.getClass());

        Optional<Field> campus = fieldList.stream()
            .filter(field -> field.getName()
                .equals("campus"))
            .findAny();

        assertTrue(campus.isPresent());
        Integer val = (Integer) campus.get()
            .get(alumn);
        assertEquals(val.intValue(), 1);
    }

    @Test
    public void readValuePrivate_thenError() throws IllegalAccessException {
        Alumn alumn = new Alumn("Peter Sampras", "A-1", 1);
        List<Field> fieldList = getAllDeclaredFields(alumn.getClass());

        Optional<Field> uid = fieldList.stream()
            .filter(field -> field.getName()
                .equals("uid"))
            .findAny();

        assertTrue(uid.isPresent());
        try {
            String val = (String) uid.get()
                .get(alumn);
            assert (false); // shouldn't get here, method is private
        } catch (IllegalAccessException iae) {
            assert (true);
        }
    }

    @Test
    public void readValuePrivateAccessible_thenOk() throws IllegalAccessException {
        Alumn alumn = new Alumn("Peter Sampras", "A-1", 1);
        List<Field> fieldList = getAllDeclaredFields(alumn.getClass());

        Optional<Field> uid = fieldList.stream()
            .filter(field -> field.getName()
                .equals("uid"))
            .findAny();

        assertTrue(uid.isPresent());
        if (Modifier.isPrivate(uid.get()
            .getModifiers()))
            uid.get()
                .setAccessible(true);

        String val = (String) uid.get()
            .get(alumn);
        assertEquals(val, "A-1");
    }

}
