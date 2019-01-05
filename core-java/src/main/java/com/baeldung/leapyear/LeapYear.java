package com.baeldung.leapyear;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

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
		boolean isLeap = Year.isLeap(2012);
		System.out.println("Is Leap year (2012) : "+isLeap);
		
		boolean isLeapBC = new GregorianCalendar().isLeapYear(-4);
		System.out.println("Is Leap year (BC 5) : "+isLeapBC);
		
		System.out.println("Is Leap year (BC 4) : "+Year.isLeap(-3));
		
		boolean isLeapFromDate = isLeapYear("2019-01-05");
		System.out.println("Is Leap year (2019-01-05): "+isLeapFromDate);
		
		isLeapFromDate = isLeapYear("2020-01-05");
		System.out.println("Is Leap year (2020-01-05): "+isLeapFromDate);
	}

}
