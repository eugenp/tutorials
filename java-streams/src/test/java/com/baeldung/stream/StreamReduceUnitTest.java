package com.baeldung.stream;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.IntSummaryStatistics;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class StreamReduceUnitTest {

    @Test
    public void givenStringArray_reduceWithAccumulator_shouldReturnConcatenatedString() {
        String[] input = { "hello", "welcome", "to", "tutorial" };
        String expectedString = "hellowelcometotutorial";        
        assertEquals(expectedString, StreamReduce.reduceWithAccumulator(input));
    }
    
    @Test
    public void givenStringArray_reduceWithAccumulatorAndIdentity_shouldReturnConcatenatedString() {
        String[] input = { "hello", "welcome", "to", "tutorial" };
        String expectedString = "hellowelcometotutorial";        
        assertEquals(expectedString, StreamReduce.reduceWithAccumulator(input));
    }
        
    @Test
    public void givenIntegerArray_reduceWithAccumulatorAndIdentity_shouldReturnConcatenatedString() {
        Integer[] input = { 10, 20, 45, 6 };
        int expectedTotal = 81;        
        assertEquals(expectedTotal, StreamReduce.reduceWithAccumulatorAndIdentity(input).intValue());
    }   

    @Test
    public void givenStringArray_reduceWithAccumulatorIdentityAndCombinery_shouldReturnSumOfLengths() {
        String[] input = { "hello", "welcome", "to", "tutorial" };
        int expectedCount = 22;        
        assertEquals(expectedCount, StreamReduce.reduceWithAccumulatorIdentityAndCombiner(input));
    }
    
    @Test
    public void givenStringArray_predefinedCount_shouldReturnCountOfElements() {
        String[] input = { "hello", "welcome", "to", "tutorial" };
        int expectedCount = 4;        
        assertEquals(expectedCount, StreamReduce.predefinedReductionCount(Arrays.asList(input)));
    }
    
    @Test
    public void givenIntegerArray_predefinedReductionSum_returnsSumOfNumbers() {
        int[] input = { 10, 20, 45, 6 };
        int expectedTotal = 81;        
        assertEquals(expectedTotal, StreamReduce.predefinedReductionSum(input));

    }
    
    @Test
    public void givenIntegerArray_predefinedReductionMin_returnsMinOfNumbers() {
        int[] input = { 10, 20, 45, 6 };
        int expectedOutput = 6;        
        assertEquals(expectedOutput, StreamReduce.predefinedReductionMin(input));

    }
    
    @Test
    public void givenIntegerArray_predefinedReductionMax_returnsMaxOfNumbers() {
        int[] input = { 10, 20, 45, 6 };
        int expectedOutput = 45;        
        assertEquals(expectedOutput, StreamReduce.predefinedReductionMax(input));

    }
    
    @Test
    public void givenIntegerArray_predefinedReductionAverage_returnsAvgOfNumbers() {
        int[] input = { 10, 20, 45, 6 };
        double expectedOutput = 20.25;        
        assertEquals(expectedOutput, StreamReduce.predefinedReductionAverage(input),0);

    }
    
    @Test
    public void givenIntegerArray_predefinedReductionSummaryStatistics_returnsExpectedValues() {
        int[] input = { 10, 20, 45, 6 };
        Double expectedAverage = 20.25;
        int expectedSum = 81;
        int expectedMin = 6;
        int expectedMax = 45;
        IntSummaryStatistics response = StreamReduce.predefinedReductionSummaryStats(input);
        assertEquals(expectedAverage, response.getAverage(), 0);
        assertEquals(expectedMin, response.getMin());
        assertEquals(expectedMax, response.getMax());
        assertEquals(expectedSum, response.getSum());
    }
       
}
