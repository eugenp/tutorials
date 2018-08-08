package com.baeldung.java9.iteraterangedate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IterateDatesCollection {

	public void iteratingRangeOfDatesJava7(List<Date> dates) {
		Iterator<Date> iterator = dates.iterator();

		for (Date date = iterator.next(); iterator.hasNext(); date = iterator.next()) {
			printDate(date);
		}
	}

	public void iteratingRangeOfDatesJava8(List<Date> dates) {
	    dates.stream()
	           .forEach(this::printDate);
	}

	private void printDate(Date date) {
		System.out.println(date);
	}
}
