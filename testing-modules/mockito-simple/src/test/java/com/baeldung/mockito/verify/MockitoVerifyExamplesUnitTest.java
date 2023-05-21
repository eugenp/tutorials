package com.baeldung.mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import com.baeldung.mockito.MyList;
import com.google.common.collect.Lists;

class MockitoVerifyExamplesUnitTest {

    @Test
    final void givenInteractionWithMock_whenVerifyingInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    final void givenOneInteractionWithMock_whenVerifyingNumberOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    final void givenNoInteractionWithWholeMock_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        verifyNoInteractions(mockedList);
    }

    @Test
    final void givenNoInteractionWithSpecificMethod_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        verify(mockedList, times(0)).size();
    }

    @Test
    final void givenUnverifiedInteraction_whenVerifyingNoUnexpectedInteractions_thenFail() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.clear();

        verify(mockedList).size();

        assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mockedList));
    }

    @Test
    final void givenInteractionsInOrder_whenVerifyingOrderOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();

        final InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    final void givenNoInteraction_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.size();

        verify(mockedList, never()).clear();
    }

    @Test
    final void givenInteractionAtLeastOnce_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();

        verify(mockedList, atLeast(1)).clear();
        verify(mockedList, atMost(10)).clear();
    }

    // with arguments

    @Test
    final void givenInteractionWithExactArgument_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.add("test");

        verify(mockedList).add("test");
    }

    @Test
    final void givenInteractionWithAnyArgument_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.add("test");

        verify(mockedList).add(anyString());
    }

    @Test
    final void givenInteraction_whenVerifyingAnInteractionWithArgumentCapture_thenCorrect() {
        final List<String> mockedList = mock(MyList.class);
        mockedList.addAll(Lists.<String> newArrayList("someElement"));

        final ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());

        final List<String> capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).contains("someElement");
    }
}
