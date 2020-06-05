package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AccessPrivateProperties {

    public void accessPrivateFields(String declaredField, Object declaredFieldValue, boolean accessible) throws NoSuchFieldException, NullPointerException, IllegalArgumentException, IllegalAccessException {
        Person person = new Person();
        Field field = person.getClass()
            .getDeclaredField(declaredField);
        field.setAccessible(accessible);
        field.set(person, declaredFieldValue);
        System.out.println(person.getName());
    }

    public void accessPrivateMethods(String declaredMethod, Class<?> parameterType, Object name, boolean accessible) throws NoSuchMethodException, IllegalAccessException, NullPointerException, IllegalArgumentException, InvocationTargetException {
        Person person = new Person();
        Method method = person.getClass()
            .getDeclaredMethod(declaredMethod, parameterType);
        method.setAccessible(accessible);
        String greetMessage = (String) method.invoke(person, name);
        System.out.println(greetMessage);
    }

}
