package com.baeldung.mockito;

import org.junit.Test;
import org.mockito.Mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MockitoConfigExamplesIntegrationTest {

    // tests

    @Test
    public final void whenMockReturnBehaviorIsConfigured_thenBehaviorIsVerified() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added, is(false));
    }

    @Test
    public final void whenMockReturnBehaviorIsConfigured2_thenBehaviorIsVerified() {
        final MyList listMock = Mockito.mock(MyList.class);
        doReturn(false).when(listMock).add(anyString());

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added, is(false));
    }

    @Test(expected = IllegalStateException.class)
    public final void givenMethodIsConfiguredToThrowException_whenCallingMethod_thenExceptionIsThrown() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
    }

    @Test(expected = NullPointerException.class)
    public final void whenMethodHasNoReturnType_whenConfiguringBehaviorOfMethod_thenPossible() {
        final MyList listMock = Mockito.mock(MyList.class);
        doThrow(NullPointerException.class).when(listMock).clear();

        listMock.clear();
    }

    @Test
    public final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingOnlyOnce_thenNoExceptionIsThrown() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
    }

    @Test(expected = IllegalStateException.class)
    public final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingTwice_thenExceptionIsThrown() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
        listMock.add(randomAlphabetic(6));
    }

    @Test
    public final void whenMockMethodCallIsConfiguredToCallTheRealMethod_thenRealMethodIsCalled() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.size()).thenCallRealMethod();

        assertThat(listMock.size(), equalTo(1));
    }

    @Test
    public final void whenMockMethodCallIsConfiguredWithCustomAnswer_thenRealMethodIsCalled() {
        final MyList listMock = Mockito.mock(MyList.class);
        doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

        final String element = listMock.get(1);
        assertThat(element, is(equalTo("Always the same")));
    }

    @Test(expected = NullPointerException.class)
    public final void givenSpy_whenConfiguringBehaviorOfSpy_thenCorrectlyConfigured() {
        final MyList instance = new MyList();
        final MyList spy = Mockito.spy(instance);

        doThrow(NullPointerException.class).when(spy).size();
        spy.size();
    }

}
