package com.baeldung.test.comparison;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SummationServiceTest {
	private static List<Integer> numbers;
	 
    @BeforeClass
    public static void initialize() {
        numbers = new ArrayList<>();
    }
 
    @AfterClass
    public static void tearDown() {
        numbers = null;
    }
 
    @Before
    public void runBeforeEachTest() {
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }
 
    @After
    public void runAfterEachTest() {
        numbers.clear();
    }
 
    @Test
    public void givenNumbers_sumEquals_thenCorrect() {
        int sum = 0;
        for (int num : numbers)
            sum += num;
        Assert.assertEquals(6, sum);
    }
    
    @Ignore
    @Test
	public void givenEmptyList_sumEqualsZero_thenCorrect(){
		 int sum = 0;
	        for (int num : numbers)
	            sum += num;
	        Assert.assertEquals(6, sum);
	}
    
    @Test(expected = ArithmeticException.class) 
    public void calculateWithException() { 
        int i = 1/0;
    }
}
