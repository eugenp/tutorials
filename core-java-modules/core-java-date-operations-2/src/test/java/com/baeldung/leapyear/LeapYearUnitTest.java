package com.baeldung.leapyear;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class LeapYearUnitTest {
	
	//Before Java8
	@Test
	public void testLeapYearUsingGregorianCalendar () {
		Assert.assertFalse(new GregorianCalendar().isLeapYear(2018));
	}
	
	//Java 8 and above
	@Test
	public void testLeapYearUsingJavaTimeYear () {
		Assert.assertTrue(Year.isLeap(2012));
	}
	
	@Test
	public void testBCYearUsingJavaTimeYear () {
		Assert.assertTrue(Year.isLeap(-4));
	}
	
	@Test
	public void testWrongLeapYearUsingJavaTimeYear () {
		Assert.assertFalse(Year.isLeap(2018));
	}
	
	@Test
	public void testLeapYearInDateUsingJavaTimeYear () {
		LocalDate date = LocalDate.parse("2020-01-05", DateTimeFormatter.ISO_LOCAL_DATE);
		Assert.assertTrue(Year.from(date).isLeap());
	}
	
}
