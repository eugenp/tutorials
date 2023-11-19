package com.baeldung.mockito.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.mockito.MyDictionary;

class MockitoExceptionUnitTest {

    @Test
    void givenNonVoidReturnType_whenUsingWhenThen_thenExceptionIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        when(dictMock.getMeaning(anyString())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> dictMock.getMeaning("word"));
    }

    @Test
    void givenVoidReturnType_whenUsingDoThrow_thenExceptionIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        doThrow(IllegalStateException.class).when(dictMock)
            .add(anyString(), anyString());

        assertThrows(IllegalStateException.class, () -> dictMock.add("word", "meaning"));
    }

    @Test
    void givenNonVoidReturnType_whenUsingWhenThenAndExeceptionAsNewObject_thenExceptionIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        when(dictMock.getMeaning(anyString())).thenThrow(new NullPointerException("Error occurred"));

        assertThrows(NullPointerException.class, () -> dictMock.getMeaning("word"));
    }

    @Test
    void givenNonVoidReturnType_whenUsingDoThrowAndExeceptionAsNewObject_thenExceptionIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        doThrow(new IllegalStateException("Error occurred")).when(dictMock).add(anyString(), anyString());

        assertThrows(IllegalStateException.class, () -> dictMock.add("word", "meaning"));

    }

    @Test
    void givenSpyAndNonVoidReturnType_whenUsingWhenThen_thenExceptionIsThrown() {
        MyDictionary dict = new MyDictionary();
        MyDictionary spy = Mockito.spy(dict);
        when(spy.getMeaning(anyString())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> spy.getMeaning("word"));
    }
}
