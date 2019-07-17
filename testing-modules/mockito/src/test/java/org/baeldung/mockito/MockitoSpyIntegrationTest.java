package org.baeldung.mockito;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSpyIntegrationTest {

    @Spy
    private List<String> aSpyList = new ArrayList<String>();

    @Test
    public void whenSpyingOnList_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = Mockito.spy(list);

        spyList.add("one");
        spyList.add("two");

        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

    @Test
    public void whenUsingTheSpyAnnotation_thenObjectIsSpied() {
        aSpyList.add("one");
        aSpyList.add("two");

        Mockito.verify(aSpyList).add("one");
        Mockito.verify(aSpyList).add("two");

        assertEquals(2, aSpyList.size());
    }

    @Test
    public void whenStubASpy_thenStubbed() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = Mockito.spy(list);

        assertEquals(0, spyList.size());

        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Test
    public void whenCreateMock_thenCreated() {
        final List<String> mockedList = Mockito.mock(ArrayList.class);

        mockedList.add("one");
        Mockito.verify(mockedList).add("one");

        assertEquals(0, mockedList.size());
    }

    @Test
    public void whenCreateSpy_thenCreate() {
        final List<String> spyList = Mockito.spy(new ArrayList<String>());

        spyList.add("one");
        Mockito.verify(spyList).add("one");

        assertEquals(1, spyList.size());
    }

}
