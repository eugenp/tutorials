package com.baeldung.java.enumiteration;

import java.util.EnumSet;

public class EnumIterationExamples {
	public static void main(String[] args) {
		System.out.println("Enum iteration using forEach:");
		EnumSet.allOf(DaysOfWeekEnum.class).forEach(day -> System.out.println(day));

		System.out.println("Enum iteration using Stream:");
		DaysOfWeekEnum.stream().filter(d -> d.getTypeOfDay().equals("off")).forEach(System.out::println);

		System.out.println("Enum iteration using for loop:");
		for (DaysOfWeekEnum day : DaysOfWeekEnum.values()) {
			System.out.println(day);
		}
	}
}
