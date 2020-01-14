package com.baeldung.datebasics;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CreateDate {
	public LocalDate getTodaysDate() {
		return LocalDate.now();
	}

	public LocalDate getTodaysDateFromClock() {
		return LocalDate.now(Clock.systemDefaultZone());
	}

	public LocalDate getTodaysDateFromZone(String zone) {
		return LocalDate.now(ZoneId.of(zone));
	}

	public LocalDate getCustomDateOne(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth);
	}

	public LocalDate getCustomDateTwo(int year, Month month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth);
	}

	public LocalDate getDateFromEpochDay(long epochDay) {
		return LocalDate.ofEpochDay(epochDay);
	}

	public LocalDate getDateFromYearAndDayOfYear(int year, int dayOfYear) {
		return LocalDate.ofYearDay(year, dayOfYear);
	}

	public LocalDate getDateFromString(String date) {
		return LocalDate.parse(date);
	}

	public LocalDate getDateFromStringAndFormatter(String date, String pattern) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
	}
}
