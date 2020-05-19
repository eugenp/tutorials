package com.baeldung.mockito;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class MockitoAnnotationsUninitializedUnitTest {

    @Mock
    List<String> mockedList;

    @Test(expected = NullPointerException.class)
    public void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
        Mockito.when(mockedList.size()).thenReturn(1);
    }
}
