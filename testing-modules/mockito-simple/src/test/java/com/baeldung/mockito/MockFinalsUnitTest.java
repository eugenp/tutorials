package com.baeldung.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class MockFinalsUnitTest {

    @Test
    public void whenMockFinalMethodMockWorks() {

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
}
