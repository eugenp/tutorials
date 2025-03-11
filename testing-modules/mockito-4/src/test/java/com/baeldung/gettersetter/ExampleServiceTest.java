package com.baeldung.gettersetter;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import net.bytebuddy.asm.Advice;

public class ExampleServiceTest {

    private final ExampleService testee = new ExampleService();

    @Test
    public void givenSimpleClass_whenInvokingGetId_thenReturnId() {
        SimpleClass simple = new SimpleClass(1L, "Jack");
        Assertions.assertEquals(testee.getId(simple), simple.getId());
    }

    @Test
    public void givenSimpleClass_whenInvokingGetName_thenReturnName() {
        SimpleClass simple = new SimpleClass(1L, "Alex");
        Assertions.assertEquals(testee.getName(simple), simple.getName());
    }

    @Test
    public void givenNonSimpleClass_whenInvokingGetName_thenReturnMockedName() {
        NonSimpleClass nonSimple = Mockito.mock(NonSimpleClass.class);
        when(nonSimple.getName()).thenReturn("Meredith");
        Assertions.assertEquals(testee.getName(nonSimple), "Meredith");
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
        NonSimpleClass nonSimple = Mockito.mock(NonSimpleClass.class);
        when(nonSimple.getName()).thenAnswer((Answer<String>) invocationOnMock -> nameWrapper.get());
        doAnswer(invocation -> {
            nameWrapper.set(invocation.getArgument(0));
            return null;
        }).when(nonSimple)
            .setName(ArgumentMatchers.anyString());
        nonSimple.setName("John");
        Assertions.assertEquals(testee.getName(nonSimple), "John");
        nonSimple.setName("Nick");
        Assertions.assertEquals(testee.getName(nonSimple), "Nick");
    }

}
