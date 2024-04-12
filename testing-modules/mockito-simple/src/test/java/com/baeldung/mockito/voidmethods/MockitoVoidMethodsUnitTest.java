package com.baeldung.mockito.voidmethods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.baeldung.mockito.MyList;

class MockitoVoidMethodsUnitTest {

    @Test
    void whenAddCalled_thenVerified() {
        MyList myList = mock(MyList.class);
        doNothing().when(myList).add(isA(Integer.class), isA(String.class));
        myList.add(0, "");

        verify(myList, times(1)).add(0, "");
    }

    @Test
    void whenAddCalled_thenVerified2() {
        MyList myList = mock(MyList.class);
        myList.add(0, "");

        verify(myList, times(1)).add(0, "");
    }

    @Test
    void givenNull_whenAddCalled_thenThrowsException() {
        MyList myList = mock(MyList.class);

        assertThrows(Exception.class, () -> {
            doThrow().when(myList).add(isA(Integer.class), isNull());
        });

        myList.add(0, null);
    }

    @Test
    void givenArgumentCaptor_whenAddCalled_thenValueCaptured() {
        MyList myList = mock(MyList.class);

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(myList).add(any(Integer.class), valueCapture.capture());

        myList.add(0, "captured");

        assertEquals("captured", valueCapture.getValue());
    }

    @Test
    void givenDoAnswer_whenAddCalled_thenAnswered() {
        MyList myList = mock(MyList.class);

        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(3, arg0);
            assertEquals("answer me", arg1);
            return null;
        }).when(myList)
            .add(any(Integer.class), any(String.class));

        myList.add(3, "answer me");
    }

    @Test
    void givenDoCallRealMethod_whenAddCalled_thenRealMethodCalled() {
        MyList myList = mock(MyList.class);

        doCallRealMethod().when(myList)
            .add(any(Integer.class), any(String.class));
        myList.add(1, "real");

        verify(myList, times(1)).add(1, "real");
    }
}
