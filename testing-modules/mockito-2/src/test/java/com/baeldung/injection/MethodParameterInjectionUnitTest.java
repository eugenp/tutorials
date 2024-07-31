package com.baeldung.injection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MethodParameterInjectionUnitTest {
    
    @Test
    void whenMockInjectedViaArgumentParameters_thenSetupCorrectly(@Mock Function<String, String> mockFunction) {
        when(mockFunction.apply("bael")).thenReturn("dung");
        assertEquals("dung", mockFunction.apply("bael"));
    }
    
    @Test
    void whenArgumentCaptorInjectedViaArgumentParameters_thenSetupCorrectly(@Mock Function<String, String> mockFunction, @Captor ArgumentCaptor<String> captor) {
        mockFunction.apply("baeldung");
        verify(mockFunction).apply(captor.capture());
        assertEquals("baeldung", captor.getValue());
    }
    
    @RepeatedTest(2)
    void whenInjectedInRepeatedTest_thenSetupCorrectly(@Mock Function<String, String> mockFunction, @Captor ArgumentCaptor<String> captor) {
        mockFunction.apply("baeldung");
        verify(mockFunction).apply(captor.capture());
        assertEquals("baeldung", captor.getValue());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"", "bael", "dung"})
    void whenInjectedInParameterizedTest_thenSetupCorrectly(String input, @Mock Function<String, String> mockFunction, @Captor ArgumentCaptor<String> captor) {
        when(mockFunction.apply(input)).thenReturn("baeldung");
        assertEquals("baeldung", mockFunction.apply(input));
        verify(mockFunction).apply(captor.capture());
        assertEquals(input, captor.getValue());
    }

}
