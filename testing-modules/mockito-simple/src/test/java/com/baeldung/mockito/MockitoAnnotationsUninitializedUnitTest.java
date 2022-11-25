package com.baeldung.mockito;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class MockitoAnnotationsUninitializedUnitTest {

    @Mock
    List<String> mockedList;

    @Test
    public void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Mockito.when(mockedList.size()).thenReturn(1);
        });

    }
}
