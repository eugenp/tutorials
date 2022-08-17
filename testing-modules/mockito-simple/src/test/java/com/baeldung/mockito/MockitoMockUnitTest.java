package com.baeldung.mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.mockito.MockSettings;
import org.mockito.exceptions.verification.TooFewActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoMockUnitTest {

    @Test
    public void whenUsingSimpleMock_thenCorrect() {
        MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }

    @Test
    public void whenUsingMockWithName_thenCorrect() {
        MyList listMock = mock(MyList.class, "myMock");
        when(listMock.add(anyString())).thenReturn(false);
        listMock.add(randomAlphabetic(6));

        assertThatThrownBy(() -> verify(listMock, times(2)).add(anyString()))
            .isInstanceOf(TooFewActualInvocations.class)
            .hasMessageContaining("myMock.add");
    }

    private static class CustomAnswer implements Answer<Boolean> {
        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return false;
        }
    }
    
    @Test
    public void whenUsingMockWithAnswer_thenCorrect() {
        MyList listMock = mock(MyList.class, new CustomAnswer());
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }

    @Test
    public void whenUsingMockWithSettings_thenCorrect() {
        MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());
        MyList listMock = mock(MyList.class, customSettings);
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }
}