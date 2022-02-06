package com.baeldung.features;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.lang.ClassLoader.getSystemClassLoader;
import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldUnitTest {

    @Test
    public void givenAnInterfaceWithDefaulMethod_whenCreatingProxyInstance_thenCanInvokeDefaultMethod() throws Exception {
        Object proxy = Proxy.newProxyInstance(getSystemClassLoader(), new Class<?>[] { HelloWorld.class },
            (prox, method, args) -> {
                if (method.isDefault()) {
                    return InvocationHandler.invokeDefault(prox, method, args);
                }
                return method.invoke(prox, args);
            }
        );
        Method method = proxy.getClass().getMethod("hello");
        assertThat(method.invoke(proxy)).isEqualTo("world");
    }
}