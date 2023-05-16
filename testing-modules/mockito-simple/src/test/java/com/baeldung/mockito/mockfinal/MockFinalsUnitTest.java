package com.baeldung.mockito.mockfinal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.jupiter.api.Test;
import org.mockito.MockMakers;

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

    @Test
    public void whenMockFinalMethodMockWorks_withInlineMockMaker() {
        MyList myList = new MyList();

        MyList mock = mock(MyList.class, withSettings().mockMaker(MockMakers.INLINE));
        when(mock.finalMethod()).thenReturn(1);

        assertThat(mock.finalMethod()).isNotEqualTo(myList.finalMethod());
    }
}
