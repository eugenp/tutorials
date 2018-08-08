package com.baeldung.java9.iteraterangedate;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class IterateDatesCollectionTest {

	List<Date> dates;

	@Test
	public void givenIteratingListOfDatesJava7_WhenStartTodayEnding10DaysAhead() {
		IterateDatesCollection iterateInColleciton = new IterateDatesCollection();

		iterateInColleciton.iteratingRangeOfDatesJava7(dates);
	}

	@Test
	public void givenIteratingListOfDatesJava8_WhenStartTodayEnding10DaysAhead() {
		IterateDatesCollection iterateInColleciton = new IterateDatesCollection();

		iterateInColleciton.iteratingRangeOfDatesJava8(dates);
	}
}
