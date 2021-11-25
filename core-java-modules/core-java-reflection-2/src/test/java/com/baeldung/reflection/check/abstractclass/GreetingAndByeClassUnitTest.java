package com.baeldung.reflection.check.abstractclass;

import com.baeldung.reflection.access.staticmethods.GreetingAndBye;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class GreetingAndByeUnitTest {

    @Test
    void givenPublicStaticMethod_whenCallWithReflection_thenReturnExpectedResult() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<GreetingAndBye> clazz = GreetingAndBye.class;
        Method method = clazz.getMethod("greeting", String.class);
        Object result = method.invoke(null, "Eric");
        Assertions.assertEquals("Hey Eric, nice to meet you!", result);
    }

    @Test
    void givenPrivateStaticMethod_whenCallWithReflection_thenReturnExpectedResult() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<GreetingAndBye> clazz = GreetingAndBye.class;
        Method method = clazz.getDeclaredMethod("goodBye", String.class);
        method.setAccessible(true);
        Object result = method.invoke(null, "Eric");
        Assertions.assertEquals("Bye Eric, see you next time.", result);
    }
}
