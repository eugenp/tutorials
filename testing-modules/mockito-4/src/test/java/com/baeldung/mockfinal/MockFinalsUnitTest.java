package com.baeldung.mockfinal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;


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
