package com.baeldung.mockito.annotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MockitoAnnotationsUninitializedUnitTest {

    @Mock
    List<String> mockedList;

    @Test
    void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
        assertThrows(NullPointerException.class, () -> {
            when(mockedList.size()).thenReturn(1);
        });
    }
}
