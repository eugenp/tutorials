package org.baeldung.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSpyTest {

    @Test
    public void whenSpyOnList_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spy = spy(list);

        spy.add("one");
        spy.add("two");

        verify(spy).add("one");
        verify(spy).add("two");

        assertEquals(2, spy.size());
    }

    @Spy
    List<String> spyList = new ArrayList<String>();

    @Test
    public void whenUseSpyAnnotation_thenCorrect() {
        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

    @Test
    public void whenStubASpy_thenStubbed() {
        final List<String> list = new ArrayList<String>();
        final List<String> spy = spy(list);

        assertEquals(0, spy.size());

        doReturn(100).when(spy).size();
        assertEquals(100, spy.size());
    }

    @Test
    public void whenCreateMock_thenCreated() {
        final List mockedList = mock(List.class);

        mockedList.add("one");
        verify(mockedList).add("one");

        assertEquals(0, mockedList.size());
    }

    @Test
    public void whenCreateSpy_thenCreate() {
        final List spy = spy(new ArrayList());

        spy.add("one");
        verify(spy).add("one");

        assertEquals(1, spy.size());
    }

}
