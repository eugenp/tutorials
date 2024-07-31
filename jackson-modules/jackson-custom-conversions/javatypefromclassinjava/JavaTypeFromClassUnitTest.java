package com.baeldung.javatypefromclassinjava;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class JavaTypeFromClassUnitTest {

    @Test
    public void givenGenericClass_whenCreatingJavaType_thenJavaTypeNotNull() {
        Class<?> myClass = MyGenericClass.class;

        JavaType javaType = TypeFactory.defaultInstance().constructType(myClass);

        assertNotNull(javaType);
    }

    @Test
    public void givenParametricType_whenCreatingJavaType_thenJavaTypeNotNull() {
        Class<?> containerClass = Container.class;
        Class<?> elementType = String.class;

        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(containerClass, elementType);

        assertNotNull(javaType);
    }

    static class MyGenericClass<T> {
        // Class implementation
    }

    static class Container<T> {
        // Class implementation
    }

}
