package com.baeldung.stream.sum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StreamSumUnitTest {

    @Test
    public void givenListOfIntegersWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumCalculator.getSumUsingCustomizedAccumulator(integers);
        assertEquals(15, sum.intValue());

    }

    @Test
    public void givenListOfIntegersWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumCalculator.getSumUsingJavaAccumulator(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingReduceThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumCalculator.getSumUsingReduce(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingCollectThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumCalculator.getSumUsingCollect(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingSumThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumCalculator.getSumUsingSum(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfItemsWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
        Item item1 = new Item(1, 10);
        Item item2 = new Item(2, 15);
        Item item3 = new Item(3, 25);
        Item item4 = new Item(4, 40);

        List<Item> items = Arrays.asList(item1, item2, item3, item4);

        Integer sum = StreamSumCalculatorWithObject.getSumUsingCustomizedAccumulator(items);
        assertEquals(90, sum.intValue());

    }

    @Test
    public void givenListOfItemsWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
        Item item1 = new Item(1, 10);
        Item item2 = new Item(2, 15);
        Item item3 = new Item(3, 25);
        Item item4 = new Item(4, 40);

        List<Item> items = Arrays.asList(item1, item2, item3, item4);

        Integer sum = StreamSumCalculatorWithObject.getSumUsingJavaAccumulator(items);
        assertEquals(90, sum.intValue());
    }

    @Test
    public void givenListOfItemsWhenSummingUsingReduceThenCorrectValueReturned() {
        Item item1 = new Item(1, 10);
        Item item2 = new Item(2, 15);
        Item item3 = new Item(3, 25);
        Item item4 = new Item(4, 40);

        List<Item> items = Arrays.asList(item1, item2, item3, item4);

        Integer sum = StreamSumCalculatorWithObject.getSumUsingReduce(items);
        assertEquals(90, sum.intValue());
    }

    @Test
    public void givenListOfItemsWhenSummingUsingCollectThenCorrectValueReturned() {
        Item item1 = new Item(1, 10);
        Item item2 = new Item(2, 15);
        Item item3 = new Item(3, 25);
        Item item4 = new Item(4, 40);

        List<Item> items = Arrays.asList(item1, item2, item3, item4);

        Integer sum = StreamSumCalculatorWithObject.getSumUsingCollect(items);
        assertEquals(90, sum.intValue());
    }

    @Test
    public void givenListOfItemsWhenSummingUsingSumThenCorrectValueReturned() {
        Item item1 = new Item(1, 10);
        Item item2 = new Item(2, 15);
        Item item3 = new Item(3, 25);
        Item item4 = new Item(4, 40);

        List<Item> items = Arrays.asList(item1, item2, item3, item4);

        Integer sum = StreamSumCalculatorWithObject.getSumUsingSum(items);
        assertEquals(90, sum.intValue());
    }

}
