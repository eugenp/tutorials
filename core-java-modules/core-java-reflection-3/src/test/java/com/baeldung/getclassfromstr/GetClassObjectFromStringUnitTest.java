package com.baeldung.getclassfromstr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetClassObjectFromStringUnitTest {
    @Test
    void givenQualifiedClsName_whenUsingClassForName_shouldGetExpectedClassObject() throws ReflectiveOperationException {
        Class cls = Class.forName("com.baeldung.getclassfromstr.MyNiceClass");
        assertNotNull(cls);

        MyNiceClass myNiceObject = (MyNiceClass) cls.getDeclaredConstructor().newInstance();
        assertNotNull(myNiceObject);
        assertEquals("Hi there, I wish you all the best!", myNiceObject.greeting());
    }

    @Test
    void givenSimpleName_whenUsingClassForName_shouldGetExpectedException() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("MyNiceClass"));
    }
}
