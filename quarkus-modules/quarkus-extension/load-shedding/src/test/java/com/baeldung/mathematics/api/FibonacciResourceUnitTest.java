package com.baeldung.mathematics.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.baeldung.mathematics.integer.sequence.FibonacciService;

public class FibonacciResourceUnitTest {
    
    @Mock
    private FibonacciService fibonacciService;

    @InjectMocks
    private FibonacciResource fibonacciResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetFibonacciSequence_thenReturnedListShouldMatchIterations() {
        int iterations = 5;
        ArrayList<Integer> expectedSequence = new ArrayList<>(Arrays.asList(0, 1, 1, 2, 3));
        when(fibonacciService.generateSequence(iterations)).thenReturn(expectedSequence);

        String result = fibonacciResource.getFibonacciSequence(iterations);
        assertEquals(expectedSequence.toString(), result);
        assertEquals(expectedSequence.size(), iterations);
    }

    @Test
    public void whenGetFibonacciSequenceWithDefaultIterations_thenResultShouldMatchExpectedSequence() {
        int defaultIterations = 10;
        ArrayList<Integer> expectedSequence = new ArrayList<>(Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34));
        when(fibonacciService.generateSequence(defaultIterations)).thenReturn(expectedSequence);

        String result = fibonacciResource.getFibonacciSequence(null);
        assertEquals(expectedSequence.toString(), result);
    }
}
