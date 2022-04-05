package com.baeldung.javac;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

    public Object run(byte[] byteCode,
                      String qualifiedClassName,
                      String methodName,
                      Class<?>[] argumentTypes,
                      Object... args)
            throws Throwable
    {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return defineClass(name, byteCode, 0, byteCode.length);
            }
        };
        Class<?> clazz;
        try {
            clazz = classLoader.loadClass(qualifiedClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load compiled test class", e);
        }

        Method method;
        try {
            method = clazz.getMethod(methodName, argumentTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can't find the 'main()' method in the compiled test class", e);
        }

        try {
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
