package com.baeldung.mockito.spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockitoSpyUnitTest {

    @Test
    void givenUsingSpyMethod_whenSpyingOnList_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertThat(spyList).hasSize(2);
    }

    @Spy
    private List<String> aSpyList = new ArrayList<String>();

    @Test
    void givenUsingSpyAnnotation_whenSpyingOnList_thenCorrect() {
        aSpyList.add("one");
        aSpyList.add("two");

        verify(aSpyList).add("one");
        verify(aSpyList).add("two");

        assertThat(aSpyList).hasSize(2);
    }

    @Test
    void givenASpy_whenStubbingTheBehaviour_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = spy(list);

        assertEquals(0, spyList.size());

        doReturn(100).when(spyList).size();

        assertThat(spyList).hasSize(100);
    }

    @Test
    void whenCreateMock_thenCreated() {
        final List<String> mockedList = mock(ArrayList.class);

        mockedList.add("one");
        verify(mockedList).add("one");

        assertThat(mockedList).hasSize(0);
    }

    @Test
    void whenCreateSpy_thenCreate() {
        final List<String> spyList = spy(new ArrayList<>());

        spyList.add("one");
        verify(spyList).add("one");

        assertThat(spyList).hasSize(1);
    }

}
