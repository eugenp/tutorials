package com.baeldung.mockito.spy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;

public class MockitoMisusingMockOrSpyUnitTest {

    @Test
    public void givenNotASpy_whenDoReturn_thenThrowNotAMock() {
        List<String> list = new ArrayList<String>();

        assertThatThrownBy(() -> Mockito.doReturn(100).when(list).size())
            .isInstanceOf(NotAMockException.class)
            .hasMessageContaining("Argument passed to when() is not a mock!");
    }
    
    @Test
    public void givenASpy_whenDoReturn_thenNoError() {
        final List<String> spyList = Mockito.spy(new ArrayList<>());
        
        assertThatNoException().isThrownBy(() -> Mockito.doReturn(100).when(spyList).size());
    }
}
