package com.baeldung.mockito_core_vs_mockito_inline;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import com.baeldung.mockito_core_vs_mockito_inline.utils.FirstUtilClass;
import com.baeldung.mockito_core_vs_mockito_inline.utils.SecondUtilClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class MockitoCoreVsMockitoInlineApplicationTests {
    @Test
    void testMockMethod() {
        FirstUtilClass firstUtilClass = Mockito.mock(FirstUtilClass.class);
        Mockito.when(firstUtilClass.getMagicNumber()).thenReturn(41);
        int result = firstUtilClass.getMagicNumber();
        assertEquals(41, result);
    }

    @Test
    void testMockStaticMethod() {
        try (MockedStatic<SecondUtilClass> mockedStatic = mockStatic(SecondUtilClass.class)) {
            mockedStatic.when(SecondUtilClass::getSecondMagicNumber).thenReturn(42);
            int result = SecondUtilClass.getSecondMagicNumber();
            assertEquals(42, result);
        }
    }
}