package com.baeldung.reflection.invokeprivatemethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

public class InvokePrivateMethodUnitTest {

    InvokePrivateMethodExample instance;
    Method privateMethod;

    @BeforeEach
    public void setup() throws Exception {
        instance = new InvokePrivateMethodExample();
        privateMethod = InvokePrivateMethodExample.class.getDeclaredMethod("getSimpleName");
    }

    @Test
    public void givenObject_whenInvokePrivateMethod_thenFail() throws Exception {
        assertThrows(IllegalAccessException.class, () -> privateMethod.invoke(instance));
    }

    @Test
    public void givenObject_whenInvokePrivateMethod_thenCorrect() throws Exception {
        privateMethod.setAccessible(true);

        assertEquals("InvokePrivateMethodExample", privateMethod.invoke(instance));
    }

    @SuppressWarnings("serial")
    @Test
    public void givenObject_whenInvokePrivateMethodWithGuava_thenFail() {
        Invokable<InvokePrivateMethodExample, ?> invokable = new TypeToken<InvokePrivateMethodExample>() {
        }.method(privateMethod);

        assertThrows(IllegalAccessException.class, () -> invokable.invoke(instance));
    }

    @SuppressWarnings("serial")
    @Test
    public void givenObject_whenInvokePrivateMethodWithGuava_thenCorrect() throws Exception {
        Invokable<InvokePrivateMethodExample, ?> invokable = new TypeToken<InvokePrivateMethodExample>() {
        }.method(privateMethod);

        invokable.setAccessible(true);

        assertEquals("InvokePrivateMethodExample", invokable.invoke(instance));
    }
}