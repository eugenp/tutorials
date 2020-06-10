package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessPrivateMethodsUnitTest {

    @Test
    public void whenGetMethod_thenSuccess() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);
        method.setAccessible(true);

        String greetMessage = (String) method.invoke(person, "John");

        Assertions.assertEquals("Hello John", greetMessage);
    }

    @Test
    public void givenInt_whenInvokeMethod_thenIllegalArgumentException() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);
        method.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> method.invoke(person, 25));
    }

    @Test
    public void whenMethodNotSetAccessible_thenIllegalAccessException() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("greet", String.class);

        Assertions.assertThrows(IllegalAccessException.class, () -> method.invoke(person, "John"));
    }

    @Test
    public void whenCallingMethodThrowsException_thenInvocationTargetException() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Person person = new Person();

        Method method = person.getClass()
            .getDeclaredMethod("divideByZeroExample");
        method.setAccessible(true);

        Assertions.assertThrows(InvocationTargetException.class, () -> method.invoke(person));
    }

}
