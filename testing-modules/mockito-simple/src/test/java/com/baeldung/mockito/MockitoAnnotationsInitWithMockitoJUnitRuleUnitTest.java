package com.baeldung.mockito;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class MockitoAnnotationsInitWithMockitoJUnitRuleUnitTest {

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();
    
    @Mock
    private List<String> mockedList;

    @Test
    public void whenUsingMockitoJUnitRule_thenMocksInitialized() {
        when(mockedList.size()).thenReturn(41);

        assertThat(mockedList.size()).isEqualTo(41);
    }
}
