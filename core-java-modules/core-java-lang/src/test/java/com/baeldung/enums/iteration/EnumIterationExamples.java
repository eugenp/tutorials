package com.baeldung.enums.iteration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;


public class EnumIterationExamples {
	public static void main(String[] args) {
		System.out.println("Enum iteration using EnumSet:");
		EnumSet.allOf(DaysOfWeekEnum.class).forEach(day -> System.out.println(day));

		System.out.println("Enum iteration using Stream:");
		DaysOfWeekEnum.stream().filter(d -> d.getTypeOfDay().equals("off")).forEach(System.out::println);

		System.out.println("Enum iteration using a for loop:");
		for (DaysOfWeekEnum day : DaysOfWeekEnum.values()) {
			System.out.println(day);
		}
		
		System.out.println("Enum iteration using Arrays.asList():");
	        Arrays.asList(DaysOfWeekEnum.values()).forEach(day -> System.out.println(day));
	        
	        System.out.println("Add Enum values to ArrayList:");
	        List<DaysOfWeekEnum> days = new ArrayList<>();
                days.add(DaysOfWeekEnum.FRIDAY);
                days.add(DaysOfWeekEnum.SATURDAY);
                days.add(DaysOfWeekEnum.SUNDAY);
                for (DaysOfWeekEnum day : days) {
                    System.out.println(day);
                }
                System.out.println("Remove SATURDAY from the list:");
                days.remove(DaysOfWeekEnum.SATURDAY); 
                if (!days.contains(DaysOfWeekEnum.SATURDAY)) { 
                    System.out.println("Saturday is no longer in the list"); 
                }
                for (DaysOfWeekEnum day : days) {
                    System.out.println(day);
                }

	}
}
