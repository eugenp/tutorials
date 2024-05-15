package com.baeldung.reflection.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;

public class DollarProxyUnitTest {
    @Test
    public void givenProxy_whenInvokingGetProxyClass_thenGeneratingProxyClass() {
        // Java 8: -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
        // Java 9 or later: -Djdk.proxy.ProxyGenerator.saveGeneratedFiles=true
        // Note: System.setProperty() doesn't work here
        // because ProxyGenerator.saveGeneratedFiles read its property only once.
        // The @Test annotation in this method will generate a $Proxy class.

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?>[] interfaces = {BasicOperation.class, AdvancedOperation.class};
        Class<?> proxyClass = Proxy.getProxyClass(classLoader, interfaces);

        boolean isProxyClass = Proxy.isProxyClass(proxyClass);
        assertTrue(isProxyClass);
    }

    @Test
    public void givenReflection_whenReadingAnnotation_thenGeneratingProxyClass() {
        FunctionalInterface instance = Consumer.class.getDeclaredAnnotation(FunctionalInterface.class);
        Class<?> clazz = instance.getClass();

        boolean isProxyClass = Proxy.isProxyClass(clazz);
        assertTrue(isProxyClass);
    }
}
