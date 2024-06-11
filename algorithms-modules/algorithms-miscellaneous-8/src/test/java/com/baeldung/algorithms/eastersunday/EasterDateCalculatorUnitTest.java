package com.baeldung.algorithms.eastersunday;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EasterDateCalculatorUnitTest {
    
    @Test
    void givenEasterInMarch_whenComputeEasterDateWithGaussAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2024, 3, 31), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(2024));
    }
    
    @Test
    void givenEasterInApril_whenComputeEasterDateWithGaussAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2004, 4, 11), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(2004));
    }
    
    @Test
    void givenEdgeCases_whenComputeEasterDateWithGaussAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(1981, 4, 19), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1981));
        assertEquals(LocalDate.of(1954, 4, 18), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1954));
    }
    
    @Test
    void givenEasterInMarch_whenComputeEasterDateWithButcherMeeusAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2024, 3, 31), new EasterDateCalculator().computeEasterDateWithButcherMeeusAlgorithm(2024));
    }
    
    @Test
    void givenEasterInApril_whenComputeEasterDateWithButcherMeeusAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2004, 4, 11), new EasterDateCalculator().computeEasterDateWithButcherMeeusAlgorithm(2004));
        // The following are added just to notice that there is no need for a special case with this algorithm
        assertEquals(LocalDate.of(1981, 4, 19), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1981));
        assertEquals(LocalDate.of(1954, 4, 18), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1954));
    }
    
    @Test
    void givenEasterInMarch_whenComputeEasterDateWithConwayAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2024, 3, 31), new EasterDateCalculator().computeEasterDateWithConwayAlgorithm(2024));
    }
    
    @Test
    void givenEasterInApril_whenComputeEasterDateWithConwayAlgorithm_thenCorrectResult() {
        assertEquals(LocalDate.of(2004, 4, 11), new EasterDateCalculator().computeEasterDateWithConwayAlgorithm(2004));
        // The following are added just to notice that there is no need for a special case with this algorithm
        assertEquals(LocalDate.of(1981, 4, 19), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1981));
        assertEquals(LocalDate.of(1954, 4, 18), new EasterDateCalculator().computeEasterDateWithGaussAlgorithm(1954));
    }

}
