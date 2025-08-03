package com.baeldung.voidmethods;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    void whenDoCallRealMethodOnMock_thenRealMethodCalled() {
        Greeting greeting = mock(Greeting.class);
        doCallRealMethod().when(greeting).sayHello(any(String.class));
        greeting.sayHello("Tom");
        verify(greeting, times(1)).sayHello("Tom");
    }

    @Test
    void whenDoCallRealMethodWithPropertyOnMock_thenRealMethodCalled() {
        Greeting greeting = mock(Greeting.class);
        doCallRealMethod().when(greeting).sayHelloWithTs(any(String.class));
        greeting.sayHelloWithTs("Jerry");
        verify(greeting, times(1)).sayHelloWithTs("Jerry");
    }

    @Test
    void whenDoCallRealMethodOnSpy_thenGetExpectedResult() {
        Greeting greeting = spy(Greeting.class);
        doCallRealMethod().when(greeting).sayHello(any(String.class));
        greeting.sayHello("Tom");
        verify(greeting, times(1)).sayHello("Tom");

        doCallRealMethod().when(greeting).sayHelloWithTs(any(String.class));
        greeting.sayHelloWithTs("Jerry");
        verify(greeting, times(1)).sayHelloWithTs("Jerry");
    }
}

class Greeting {
    private static final Logger LOG = LoggerFactory.getLogger(Greeting.class);
    private Instant timestamp = Instant.now();

    public void sayHello(String name) {
        LOG.info("Hi {}, how are you?", name);
    }

    public void sayHelloWithTs(String name) {
        LOG.info("Hi {}, how are you? [Greeting Created at {}]", name, timestamp);
    }
}