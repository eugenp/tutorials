package com.baeldung.mockito;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockFinals {

      @Test
      public void whenMockFinalClassMockWorks() {

        FinalList finalList = new FinalList();

        FinalList mock = mock(FinalList.class);
        when(mock.size()).thenReturn(2);

        assertNotEquals(mock.size(), finalList.size());

      }

    @Test
    public void whenMockFinalMethodMockWorks() {

      MyList myList = new MyList();

      MyList mock = mock(MyList.class);
      when(mock.finalMethod()).thenReturn(1);

      assertNotEquals(mock.finalMethod(), myList.finalMethod());
    }


  }
