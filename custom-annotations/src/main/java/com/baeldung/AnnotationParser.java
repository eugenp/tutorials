package com.baeldung;

import com.baeldung.annotations.MethodDescription;
import com.baeldung.annotations.ZipCode;
import com.baeldung.domain.Address;
import com.baeldung.domain.AddressService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {

    /**
     * Validates @param object to see if @ZipCode fields are properly populated
     */
    public static <T> void validate(T object) throws Exception {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(ZipCode.class)) {
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    try {
                        int value = Integer.parseInt(fieldValue.toString());
                        if (value < 100000 || value > 999999) {
                            throw new Exception("@ZipCode field must be 6 digits in " + object.toString());
                        }
                    } catch (NumberFormatException e) {
                        throw new Exception("@ZipCode field must be numeric " + object.toString());
                    }
                }
            }
        }
    }

    /**
     * Lists down method descriptions in the @param klass
     */
    public static <T> void listMethodDescriptions(Class klass) {
        System.out.println("Printing method descriptions in " + klass.getSimpleName());
        System.out.println();

        int index = 1;
        for (Method method : klass.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(MethodDescription.class)) {
                MethodDescription annotation = method.getAnnotation(MethodDescription.class);
                System.out.println(index + ") " + "Description: " + annotation.value() + ", Author: " + annotation.author() + ", Version: " + annotation.version());
                index++;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Address address = new Address("12345", "New York", "US");
            validate(address);
            System.out.println(address.toString() + " is a valid object");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Address address = new Address("123456", "New Delhi", "India");
            validate(address);
            System.out.println(address.toString() + " is a valid object");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Address address = new Address("1234567", "London", "UK");
            validate(address);
            System.out.println(address.toString() + " is a valid object");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Address address = new Address("12345S", "Los Angeles", "US");
            validate(address);
            System.out.println(address.toString() + " is a valid object");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        listMethodDescriptions(AddressService.class);
    }
}
