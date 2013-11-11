package org.baeldung.mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class MockitoConfigExamplesTest {

    // tests

    @Test
    public final void whenMockBehaviorIsConfigured_thenBehaviorIsVerified() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);

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

}
