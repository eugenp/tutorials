package com.baeldung.gettersetter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.stubbing.Answer;

public class ExampleServiceTest {

    @Test
    public void givenMockedSimpleClass_whenInvokingSettersGetters_thenInvokeMockedSettersGetters() {
        Long mockId = 12L;
        String mockName = "I'm 12";
        SimpleClass simpleMock = mock(SimpleClass.class);
        when(simpleMock.getId()).thenReturn(mockId);
        when(simpleMock.getName()).thenReturn(mockName);
        doNothing().when(simpleMock)
            .setId(anyLong());
        doNothing().when(simpleMock)
            .setName(anyString());
        ExampleService srv = new ExampleService();
        srv.setField(simpleMock::setId, 11L);
        srv.setField(simpleMock::setName, "I'm 11");
        assertEquals(srv.getField(simpleMock::getId), mockId);
        assertEquals(srv.getField(simpleMock::getName), mockName);
        verify(simpleMock).getId();
        verify(simpleMock).getName();
        verify(simpleMock).setId(eq(11L));
        verify(simpleMock).setName(eq("I'm 11"));
    }

    @Test
    public void givenActualSimpleClass_whenInvokingSettersGetters_thenInvokeActualSettersGetters() {
        Long id = 1L;
        String name = "I'm 1";
        SimpleClass simple = new SimpleClass(id, name);
        ExampleService srv = new ExampleService();
        srv.setField(simple::setId, 2L);
        srv.setField(simple::setName, "I'm 2");
        assertEquals(srv.getField(simple::getId), simple.getId());
        assertEquals(srv.getField(simple::getName), simple.getName());
    }

    @Test
    public void givenNonSimpleClass_whenInvokingGetName_thenReturnMockedName() {
        NonSimpleClass nonSimple = mock(NonSimpleClass.class);
        when(nonSimple.getName()).thenReturn("Meredith");
        ExampleService srv = new ExampleService();
        assertEquals(srv.getField(nonSimple::getName), "Meredith");
        verify(nonSimple).getName();
    }

    static class Wrapper<T> {

        private T value;

        Wrapper(T value) {
            this.value = value;
        }

        Wrapper(Class<T> value) {

        }

        T get() {
            return value;
        }

        void set(T value) {
            this.value = value;
        }

    }

    @Test
    public void givenNonSimpleClass_whenInvokingGetName_thenReturnTheLatestNameSet() {
        Wrapper<String> nameWrapper = new Wrapper<>(String.class);
        NonSimpleClass nonSimple = mock(NonSimpleClass.class);
        when(nonSimple.getName()).thenAnswer((Answer<String>) invocationOnMock -> nameWrapper.get());
        doAnswer(invocation -> {
            nameWrapper.set(invocation.getArgument(0));
            return null;
        }).when(nonSimple)
            .setName(anyString());
        ExampleService srv = new ExampleService();
        srv.setField(nonSimple::setName, "John");
        assertEquals(srv.getField(nonSimple::getName), "John");
        srv.setField(nonSimple::setName, "Nick");
        assertEquals(srv.getField(nonSimple::getName), "Nick");
    }

}
