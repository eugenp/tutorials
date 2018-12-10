package com.baeldung.commons.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class CourseService {

    public static void setValues(Course course, String name, List<String> codes) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Setting the simple properties
        PropertyUtils.setSimpleProperty(course, "name", name);
        PropertyUtils.setSimpleProperty(course, "codes", codes);
    }

    public static void setIndexedValue(Course course, int codeIndex, String code) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Setting the indexed properties
        PropertyUtils.setIndexedProperty(course, "codes[" + codeIndex + "]", code);
    }

    public static void setMappedValue(Course course, String enrollId, Student student) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Setting the mapped properties
        PropertyUtils.setMappedProperty(course, "enrolledStudent(" + enrollId + ")", student);
    }

    public static String getNestedValue(Course course, String enrollId, String nestedPropertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return (String) PropertyUtils.getNestedProperty(course, "enrolledStudent(" + enrollId + ")." + nestedPropertyName);
    }

    public static void copyProperties(Course course, CourseEntity courseEntity) throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(course, courseEntity);
    }
}
