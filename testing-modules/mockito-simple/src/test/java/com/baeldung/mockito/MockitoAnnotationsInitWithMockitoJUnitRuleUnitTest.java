package com.baeldung.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockitoAnnotationsInitWithMockitoJUnitRuleUnitTest {

    @Mock
    private List<String> mockedList;

    @Test
    public void whenUsingMockitoJUnitRule_thenMocksInitialized() {
        when(mockedList.size()).thenReturn(41);

        assertThat(mockedList).hasSize(41);
    }
}
