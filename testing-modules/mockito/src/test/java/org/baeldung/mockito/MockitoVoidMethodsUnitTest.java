package org.baeldung.mockito;

import static org.junit.Assert.assertEquals;
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class MockitoVoidMethodsUnitTest {

    @Test
    public void whenAddCalledVerified() {
        MyList mockVoid = mock(MyList.class);
        mockVoid.add(0, "");
        verify(mockVoid, times(1)).add(0, "");
    }

    @Test(expected = Exception.class)
    public void givenNull_addThrows() {
        MyList mockVoid = mock(MyList.class);
        doThrow().when(mockVoid).add(isA(Integer.class), isNull());
        mockVoid.add(0, null);
    }

    @Test
    public void whenAddCalledValueCaptured() {
        MyList mockVoid = mock(MyList.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockVoid).add(any(Integer.class), valueCapture.capture());
        mockVoid.add(0, "captured");
        assertEquals("captured", valueCapture.getValue());
    }

    @Test
    public void whenAddCalledAnswered() {
        MyList mockVoid = mock(MyList.class);
        doAnswer((Answer<Void>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            //do something with the arguments here
            assertEquals(3, arg0);
            assertEquals("answer me", arg1);

            return null;
        }).when(mockVoid).add(any(Integer.class), any(String.class));
        mockVoid.add(3, "answer me");
    }

    @Test
    public void whenAddCalledRealMethodCalled() {
        MyList mockVoid = mock(MyList.class);
        doCallRealMethod().when(mockVoid).add(any(Integer.class), any(String.class));
        mockVoid.add(1, "real");
        verify(mockVoid, times(1)).add(1, "real");
    }
}
