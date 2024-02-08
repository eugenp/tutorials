package com.baeldung.injection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConstructorInjectionUnitTest {
    
    Function<String, String> function;
    
    ArgumentCaptor<String> captor;
    
    public ConstructorInjectionUnitTest(@Mock Function<String, String> function, @Captor ArgumentCaptor<String> captor) {
        this.function = function;
        this.captor = captor;
    }
    
    @Test
    void whenInjectedViaArgumentParameters_thenSetupCorrectly() {
        when(function.apply("bael")).thenReturn("dung");
        assertEquals("dung", function.apply("bael"));
        verify(function).apply(captor.capture());
        assertEquals("bael", captor.getValue());
    }

}
