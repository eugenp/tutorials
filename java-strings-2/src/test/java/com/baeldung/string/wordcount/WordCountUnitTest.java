package com.baeldung.string.wordcount;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCountUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(WordCountUnitTest.class);

    String stringIn = " I  like   Cookies, Cake & Icecream but not chocolates! ";

    String stringTocheck = stringIn.trim()
        .replaceAll(" +", " ");

    @Test
    public void testCountUsingforLoop() {
        int count = WordCount.countUsingCharAt(stringTocheck, ' ');
        assertEquals(9, count);
    }

    @Test
    public void testCountUsingSplit() {
        int count = WordCount.countUsingSplit(stringTocheck, " ");
        logger.info("Total no of words in split are: " + count);
        assertEquals(9, count);
    }

    @Test
    public void testCountUsingSplitPuntuation() {
        int count = WordCount.countUsingSplit(stringTocheck, ",");
        logger.info("Total no of words in split seperated by ',' are: " + count);
        assertEquals(2, count);
    }

    @Test
    public void testCountUsingStringTokenizer() {
        int count = WordCount.countUsingStringTokenizer(stringTocheck, " ");
        assertEquals(9, count);
    }

    @Test
    public void testCountUsingStringTokenizerPunctuation() {
        int count = WordCount.countUsingStringTokenizer(stringTocheck, ",");
        assertEquals(2, count);
    }

    @Test
    public void testCountUsingApacheStringUtils() {
        int count = WordCount.countUsingApacheStringUtils(stringTocheck, " ");
        assertEquals(9, count);
    }

    // Please include the spring-core dependency and then uncomment below code, to test the StringUtils of spring framework.

    /*
    @Test
    public void testCountUsingSpringStringUtils() {
        int count = WordCount.countUsingSpringStringUtils(stringTocheck, " ");
        assertEquals(9,count);
    }
    */

}
