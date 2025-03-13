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

import com.baeldung.mathematics.integer.sequence.FactorialService;

public class FactorialResourceUnitTest {
    @Mock
    private FactorialService factorialService;

    @InjectMocks
    private FactorialResource factorialResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRequestFactorialSequence_thenReturnedListShouldMatchIterations() {
        int iterations = 5;
        ArrayList<Long> expectedSequence = new ArrayList<>(Arrays.asList(1L, 1L, 2L, 6L, 24L));
        when(factorialService.generateSequence(iterations)).thenReturn(expectedSequence);

        String result = factorialResource.getFactorialSequence(iterations);
        assertEquals(expectedSequence.toString(), result);
        assertEquals(expectedSequence.size(), iterations);
    }

    @Test
    public void whenGetFactorialSequenceWithDefaultIterations_thenResultShouldBeASequenceOfTenValues() {
        int defaultIterations = 10;
        ArrayList<Long> expectedSequence = new ArrayList<>(Arrays.asList(1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L));
        when(factorialService.generateSequence(defaultIterations)).thenReturn(expectedSequence);

        String result = factorialResource.getFactorialSequence(null);
        assertEquals(expectedSequence.toString(), result);
    }
}
