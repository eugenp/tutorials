package com.baeldung.passclassasparameter;

import java.lang.reflect.Method;

public class ReflectionExample {
    public static void processClass(Class<?> clazz, String methodName) throws Exception {
        Method method = clazz.getMethod(methodName);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        method.invoke(instance);
    }

    public static void main(String[] args) throws Exception {
        processClass(ReflectionTarget.class, "sayHello");
    }
}

class ReflectionTarget {
    public void sayHello() {
        System.out.println("Hello, Reflection!");
    }
}