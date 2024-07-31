package com.baeldung.mockito.mockfinal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.baeldung.mockito.FinalList;
import com.baeldung.mockito.MyList;

class MockFinalsUnitTest {

    @Test
    void whenMockFinalMethod_thenMockWorks() {

        MyList mock = mock(MyList.class);
        when(mock.finalMethod()).thenReturn(1);

        assertThat(mock.finalMethod()).isNotZero();
    }

    @Test
    void whenMockFinalClass_thenMockWorks() {

        FinalList mock = mock(FinalList.class);
        when(mock.size()).thenReturn(2);

        assertThat(mock.size()).isNotEqualTo(1);
    }
}
