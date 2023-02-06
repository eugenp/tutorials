package com.baeldung.firstoccurrenceofaninteger;

public class FirstOccurrenceOfAnInteger {

	public static Integer findFirstInteger(String s) {
		int i = 0;
		while (i < s.length() && !Character.isDigit(s.charAt(i))) {
			i++;
		}
		int j = i;
		while (j < s.length() && Character.isDigit(s.charAt(j))) {
			j++;
		}
		return Integer.parseInt(s.substring(i, j));
	}
}
