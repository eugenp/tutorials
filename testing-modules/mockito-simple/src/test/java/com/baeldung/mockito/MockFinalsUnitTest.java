package com.baeldung.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.jupiter.api.Test;
import org.mockito.MockMakers;

class MockFinalsUnitTest {

    @Test
    void whenMockFinalMethodMockWorks() {

        MyList myList = new MyList();

        MyList mock = mock(MyList.class);
        when(mock.finalMethod()).thenReturn(1);

        assertThat(mock.finalMethod()).isNotEqualTo(myList.finalMethod());
    }

    @Test
    public void whenMockFinalClassMockWorks() {

        FinalList finalList = new FinalList();

        FinalList mock = mock(FinalList.class);
        when(mock.size()).thenReturn(2);

        assertThat(mock.size()).isNotEqualTo(finalList.size());
    }

    @Test
    public void whenMockFinalMethodMockWorks_withInlineMockMaker() {
        MyList myList = new MyList();

        MyList mock = mock(MyList.class, withSettings().mockMaker(MockMakers.INLINE));
        when(mock.finalMethod()).thenReturn(1);

        assertThat(mock.finalMethod()).isNotEqualTo(myList.finalMethod());
    }
}
