package com.baeldung.leapyear;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

//Run with -ea or -enableassertions VM argument
public class LeapYear {
	/**
	 * Checks whether the year in the given date is leap year or not using {@link java.time.Year} API.
	 * 
	 * @param isoDate date string ISO_LOCAL_DATE format of pattern "YYYY-MM-dd". Example: 2011-12-03
	 * @return true if the year in the given date is leap year, otherwise false.
	 */
	public static boolean isLeapYear(String isoDate) {
		LocalDate date = LocalDate.parse(isoDate, DateTimeFormatter.ISO_LOCAL_DATE);
		
		return Year.from(date).isLeap();
	}
	
	public static void main (String args[]) {
		//Before Java 8
		assert new GregorianCalendar().isLeapYear(-4); 

		//Java 8 and above
		assert Year.isLeap(-3) == false;
		
		assert Year.isLeap(2012);
		
		assert !isLeapYear("2019-01-05");
		
		assert isLeapYear("2020-01-05");
	}

}
