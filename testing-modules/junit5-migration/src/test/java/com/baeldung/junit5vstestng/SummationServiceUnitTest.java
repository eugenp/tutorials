package com.baeldung.junit5vstestng;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SummationServiceUnitTest {
    private static List<Integer> numbers;

    @BeforeAll
    public static void initialize() {
        numbers = new ArrayList<>();
    }
 
    @AfterAll
    public static void tearDown() {
        numbers = null;
    }
 
    @BeforeEach
    public void runBeforeEachTest() {
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }
 
    @AfterEach
    public void runAfterEachTest() {
        numbers.clear();
    }
 
    @Test
    public void givenNumbers_sumEquals_thenCorrect() {
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        Assert.assertEquals(6, sum);
    }

    @Ignore
    @Test
    public void givenEmptyList_sumEqualsZero_thenCorrect() {
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        Assert.assertEquals(6, sum);
    }
}
