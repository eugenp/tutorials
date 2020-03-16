package com.baeldung.getweeknumber;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GetWeekNumberNewJava {
	private LocalDate date;

	public GetWeekNumberNewJava(String day, String dayFormat) {
		date = LocalDate.parse(day, DateTimeFormatter.ofPattern(dayFormat));
	}
	
	public GetWeekNumberNewJava(int year, int month, int day) {
		date = LocalDate.of(year, month, day); 
	}
	
	public DayOfWeek getDayOfWeek() {
		return date.getDayOfWeek();
	}
	
	public static void main(String[] args) {
		GetWeekNumberNewJava example1 = new GetWeekNumberNewJava(2020, 3, 2);
		System.out.println(example1.getDayOfWeek());

		GetWeekNumberNewJava example2 = new GetWeekNumberNewJava("20200302", "yyyyMMdd");
		System.out.println(example2.getDayOfWeek().getValue());
	}
}
