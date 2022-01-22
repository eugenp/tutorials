package com.baeldung.mockito;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockSettings;
import org.mockito.exceptions.verification.TooFewActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MockitoMockIntegrationTest {

    private static class CustomAnswer implements Answer<Boolean> {
        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return false;
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenUsingSimpleMock_thenCorrect() {
        MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }

    @Test
    public void whenUsingMockWithName_thenCorrect() {
        MyList listMock = mock(MyList.class, "myMock");
        when(listMock.add(anyString())).thenReturn(false);
        listMock.add(randomAlphabetic(6));
        
        thrown.expect(TooFewActualInvocations.class);
        thrown.expectMessage(containsString("myMock.add"));

        verify(listMock, times(2)).add(anyString());
    }

    @Test
    public void whenUsingMockWithAnswer_thenCorrect() {
        MyList listMock = mock(MyList.class, new CustomAnswer());
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }

    @Test
    public void whenUsingMockWithSettings_thenCorrect() {
        MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());
        MyList listMock = mock(MyList.class, customSettings);
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }
}