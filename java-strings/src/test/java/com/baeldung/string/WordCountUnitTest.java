package com.baeldung.string;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author shwetaJ
 */

public class WordCountUnitTest {
	private static final Logger logger = LoggerFactory.getLogger(WordCountUnitTest.class);

	String stringIn = "I like  Cookies, Cake & Icecream but not chocolates! ";

	String stringTocheck = stringIn.trim().replaceAll(" +", " ");

	@Test
	public void testCountUsingforLoop() {
		int count = WordCount.countUsingCharAt(stringTocheck, ' ');
		assertEquals(count, 9);
	}

	/*
	@Test
	public void testCountUsingSpringStringUtils() {
		int count = WordCount.countUsingSpringStringUtils(stringTocheck, " ");
		assertEquals(count, 9);
	}
	*/

	@Test
	public void testCountUsingSplit() {
		int count = WordCount.countUsingSplit(stringTocheck, ",");
		assertEquals(count, 2);
	}

	@Test
	public void testCountUsingStringTokenizer() {
		int count = WordCount.countUsingStringTokenizer(stringTocheck, " ");
		assertEquals(count, 9);
	}

	@Test
	public void testCountUsingStringTokenizerPunctuation() {
		int count = WordCount.countUsingStringTokenizer(stringTocheck, ",");
		assertEquals(count, 2);
	}

	@Test
	public void testCountUsingApacheStringUtils() {
		int count = WordCount.countUsingApacheStringUtils(stringTocheck, " ");
		assertEquals(count, 9);
	}
}
