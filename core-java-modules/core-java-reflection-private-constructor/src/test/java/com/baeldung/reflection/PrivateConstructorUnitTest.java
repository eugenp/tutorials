package com.baeldung.reflection;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrivateConstructorUnitTest {

    @Test
    public void whenConstructorIsPrivate_thenInstanceSuccess() throws Exception {
        Constructor<PrivateConstructorClass> pcc = PrivateConstructorClass.class.getDeclaredConstructor();
        pcc.setAccessible(true);
        PrivateConstructorClass privateConstructorInstance = pcc.newInstance();
        Assertions.assertTrue(privateConstructorInstance instanceof PrivateConstructorClass);
    }
}