package com.baeldung.undeclared;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UndeclaredThrowableExceptionUnitTest {

    @Test
    @SuppressWarnings("unchecked")
    public void givenAProxy_whenProxyUndeclaredThrowsCheckedException_thenShouldBeWrapped() {
        ClassLoader classLoader = getClass().getClassLoader();
        InvocationHandler invocationHandler = new ExceptionalInvocationHandler();
        List<String> proxy = (List<String>) Proxy.newProxyInstance(classLoader, new Class[] { List.class }, invocationHandler);

        assertThatThrownBy(proxy::size)
          .isInstanceOf(UndeclaredThrowableException.class)
          .hasCauseInstanceOf(SomeCheckedException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void givenAProxy_whenProxyThrowsUncheckedException_thenShouldBeThrownAsIs() {
        ClassLoader classLoader = getClass().getClassLoader();
        InvocationHandler invocationHandler = new ExceptionalInvocationHandler();
        List<String> proxy = (List<String>) Proxy.newProxyInstance(classLoader, new Class[] { List.class }, invocationHandler);

        assertThatThrownBy(proxy::isEmpty).isInstanceOf(RuntimeException.class);
    }

    private static class ExceptionalInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("size".equals(method.getName())) {
                throw new SomeCheckedException("Always fails");
            }

            throw new RuntimeException();
        }
    }
}
