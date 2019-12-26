package com.baeldung.mockito;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MockitoExceptionIntegrationTest {

    @Test(expected = NullPointerException.class)
    public void whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        when(dictMock.getMeaning(anyString())).thenThrow(NullPointerException.class);

        dictMock.getMeaning("word");
    }

    @Test(expected = IllegalStateException.class)
    public void whenConfigVoidRetunMethodToThrowEx_thenExIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        doThrow(IllegalStateException.class).when(dictMock)
            .add(anyString(), anyString());

        dictMock.add("word", "meaning");
    }

    @Test(expected = NullPointerException.class)
    public void whenConfigNonVoidRetunMethodToThrowExWithNewExObj_thenExIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        when(dictMock.getMeaning(anyString())).thenThrow(new NullPointerException("Error occurred"));

        dictMock.getMeaning("word");
    }

    @Test(expected = IllegalStateException.class)
    public void whenConfigVoidRetunMethodToThrowExWithNewExObj_thenExIsThrown() {
        MyDictionary dictMock = mock(MyDictionary.class);
        doThrow(new IllegalStateException("Error occurred")).when(dictMock)
            .add(anyString(), anyString());

        dictMock.add("word", "meaning");
    }

    // =====

    @Test(expected = NullPointerException.class)
    public void givenSpy_whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() {
        MyDictionary dict = new MyDictionary();
        MyDictionary spy = Mockito.spy(dict);

        when(spy.getMeaning(anyString())).thenThrow(NullPointerException.class);
        spy.getMeaning("word");
    }
}
