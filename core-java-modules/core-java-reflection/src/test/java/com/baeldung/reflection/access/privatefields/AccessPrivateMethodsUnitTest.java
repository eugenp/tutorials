package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessPrivateMethodsUnitTest {

    @Test
    public void whenGetMethod_thenSuccess() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);
        method.setAccessible(true);

        String greetMessage = (String) method.invoke(person, "Jane");

        Assertions.assertEquals("Hello Jane", greetMessage);
    }

    @Test
    public void givenInt_whenInvokeMethod_thenIllegalArgumentException() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);
        method.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> method.invoke(person, 25));
    }
    
    @Test
    public void givenMultipleParameters_whenInvokeMethod_thenIllegalArgumentException() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);
        method.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> method.invoke(person, "John", "Jane"));
    }

    @Test
    public void whenMethodNotSetAccessible_thenIllegalAccessException() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);

        Assertions.assertThrows(IllegalAccessException.class, () -> method.invoke(person, "Jane"));
    }

    @Test
    public void whenCallingMethodThrowsException_thenInvocationTargetException() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NullPointerException {
        Person person = new Person();
        Method method = person.getClass()
            .getDeclaredMethod("divideByZeroExample");
        method.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, () -> method.invoke(person));
    }

    @Test
    public void whenAccessingWrongProperty_thenNoSuchMethodException() throws NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Assertions.assertThrows(NoSuchMethodException.class, () -> person.getClass()
            .getDeclaredMethod("greeting", String.class));
    }

    @Test
    public void whenAccessingNullMethod_thenNullPointerException() throws NoSuchMethodException, NullPointerException {
        Person person = new Person();

        Assertions.assertThrows(NullPointerException.class, () -> person.getClass()
            .getDeclaredMethod(null, String.class));
    }

}
