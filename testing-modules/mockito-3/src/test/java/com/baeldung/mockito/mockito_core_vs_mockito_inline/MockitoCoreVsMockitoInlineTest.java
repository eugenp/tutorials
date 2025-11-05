package com.baeldung.mockito.mockito_core_vs_mockito_inline;

import com.baeldung.mockito.utils.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.mockito.MockedStatic;
import org.mockito.MockedConstruction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MockitoCoreVsMockitoInlineTests {
    @Test
    void testFinalClassMock() {
        FinalClass finalClass = mock(FinalClass.class);
        when(finalClass.greet()).thenReturn("Mocked Greeting");

        assertEquals("Mocked Greeting", finalClass.greet());
    }

    @Test
    void testFinalFieldMock() {
        ClassWithFinalField instance = mock(ClassWithFinalField.class);
        when(instance.getFinalField()).thenReturn("Mocked Value");

        assertEquals("Mocked Value", instance.getFinalField());
    }

    @Test
    void testConstructorMock() {
        try (MockedConstruction<ClassWithConstructor> mocked = mockConstruction(ClassWithConstructor.class,
                (mock, context) -> when(mock.getName()).thenReturn("Mocked Name"))) {

            ClassWithConstructor myClass = new ClassWithConstructor("test");
            assertEquals("Mocked Name", myClass.getName());
        }
    }

    @Test
    void testStaticMethodMock() {
        try (MockedStatic<ClassWithStaticMethod> mocked = mockStatic(ClassWithStaticMethod.class)) {
            mocked.when(ClassWithStaticMethod::staticMethod).thenReturn("Mocked Static Value");

            assertEquals("Mocked Static Value", ClassWithStaticMethod.staticMethod());
        }
    }
}