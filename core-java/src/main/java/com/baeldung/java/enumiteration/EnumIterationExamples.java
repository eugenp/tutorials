package com.baeldung.java.enumiteration;

import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author zn.wang
 */
public class EnumIterationExamples {

	public static void main(String[] args) {
		System.out.println("Enum iteration using forEach:");
		for (DaysOfWeekEnum daysOfWeekEnum : EnumSet.allOf(DaysOfWeekEnum.class)) {
			System.out.println(daysOfWeekEnum);
		}

		System.out.println("\n=========================\n");
		System.out.println("Enum iteration using Stream:");
		DaysOfWeekEnum.stream().filter(new Predicate<DaysOfWeekEnum>() {
			@Override
			public boolean test(DaysOfWeekEnum d) {
				return d.getTypeOfDay().equals("off");
			}
		}).forEach(new Consumer<DaysOfWeekEnum>() {
			@Override
			public void accept(DaysOfWeekEnum x) {
				System.out.println(x);
			}
		});

		System.out.println("\n=========================\n");
		System.out.println("Enum iteration using for loop:");
		for (DaysOfWeekEnum day : DaysOfWeekEnum.values()) {
			System.out.println(day);
		}
	}
}
