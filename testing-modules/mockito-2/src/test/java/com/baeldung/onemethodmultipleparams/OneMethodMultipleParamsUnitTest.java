package com.baeldung.onemethodmultipleparams;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OneMethodMultipleParamsUnitTest {

    @Mock
    ExampleService exampleService;

    @Test
    public void givenAMethod_whenStubbingForMultipleArguments_thenExpectDifferentResults() {
        when(exampleService.getValue(10)).thenReturn(100);
        when(exampleService.getValue(20)).thenReturn(200);
        when(exampleService.getValue(30)).thenReturn(300);

        assertEquals(100, exampleService.getValue(10));
        assertEquals(200, exampleService.getValue(20));
        assertEquals(300, exampleService.getValue(30));
    }

    @Test
    public void givenAMethod_whenUsingThenAnswer_thenExpectDifferentReults() {
        when(exampleService.getValue(anyInt())).thenAnswer(invocation -> {
            int argument = (int) invocation.getArguments()[0];
            int result;
            switch (argument) {
            case 25:
                result = 125;
                break;
            case 50:
                result = 150;
                break;
            case 75:
                result = 175;
                break;
            default:
                result = 0;
            }
            return result;
        });
        assertEquals(125, exampleService.getValue(25));
        assertEquals(150, exampleService.getValue(50));
        assertEquals(175, exampleService.getValue(75));
    }

    @Test
    public void givenAMethod_whenUsingConsecutiveStubbing_thenExpectResultsInOrder() {
        when(exampleService.getValue(anyInt())).thenReturn(9, 18, 27);
        assertEquals(9, exampleService.getValue(1));
        assertEquals(18, exampleService.getValue(1));
        assertEquals(27, exampleService.getValue(1));
        assertEquals(27, exampleService.getValue(1));
    }

}
