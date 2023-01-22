package com.baeldung.mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;


class MockitoWhenThenExamplesUnitTest {

    @Test
    final void whenMockReturnBehaviorIsConfigured_thenBehaviorIsVerified() {
        final MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    final void whenMockReturnBehaviorIsConfigured2_thenBehaviorIsVerified() {
        final MyList listMock = mock(MyList.class);
        doReturn(false).when(listMock).add(anyString());

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    final void givenMethodIsConfiguredToThrowException_whenCallingMethod_thenExceptionIsThrown() {
        final MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> {
            listMock.add(randomAlphabetic(6));
        });

    }

    @Test
    final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingOnlyOnce_thenNoExceptionIsThrown() {
        final MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
    }
    
    @Test
    final void whenMethodHasNoReturnType_whenConfiguringBehaviorOfMethod_thenPossible() {
        final MyList listMock = mock(MyList.class);
        doThrow(NullPointerException.class).when(listMock).clear();

        assertThrows(NullPointerException.class, () -> {
            listMock.clear();
        });

    }

    @Test
    final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingTwice_thenExceptionIsThrown() {
        final MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> {
            listMock.add(randomAlphabetic(6));
            listMock.add(randomAlphabetic(6));
        });

    }

    @Test
    final void givenSpy_whenConfiguringBehaviorOfSpy_thenCorrectlyConfigured() {
        final MyList instance = new MyList();
        final MyList spy = spy(instance);

        doThrow(NullPointerException.class).when(spy).size();
        assertThrows(NullPointerException.class, () -> {
            spy.size();
        });

    }
    
    @Test
    final void whenMockMethodCallIsConfiguredToCallTheRealMethod_thenRealMethodIsCalled() {
        final MyList listMock = mock(MyList.class);
        when(listMock.size()).thenCallRealMethod();

        assertThat(listMock).hasSize(1);
    }
    
    @Test
    final void whenMockMethodCallIsConfiguredWithCustomAnswer_thenRealMethodIsCalled() {
        final MyList listMock = mock(MyList.class);
        doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

        final String element = listMock.get(1);
        assertThat(element).isEqualTo("Always the same");
    }
}
