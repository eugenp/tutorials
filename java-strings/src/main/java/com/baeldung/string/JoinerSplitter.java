package com.baeldung.string;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinerSplitter {

	public static String join ( String[] arrayOfString ) {
		return Arrays.asList(arrayOfString)
				.stream()
				.map(x -> x)
				.collect(Collectors.joining(","));
	}
	
	public static String joinWithPrefixPostFix ( String[] arrayOfString ) {
		return Arrays.asList(arrayOfString)
				.stream()
				.map(x -> x)
				.collect(Collectors.joining(",","[","]"));
	}
	
	public static List<String> split ( String str ) {
		return Stream.of(str.split(","))
				.map (elem -> new String(elem))
				.collect(Collectors.toList());
	}
	
	public static List<Character> splitToListOfChar ( String str ) {
		return str.chars()
				.mapToObj(item -> (char) item)
				.collect(Collectors.toList());
	}
	
	public static Map<String, String> arrayToMap(String[] arrayOfString) {
		return Arrays.asList(arrayOfString)
				.stream()
				.map(str -> str.split(":"))
				.collect(Collectors.<String[], String, String>toMap(str -> str[0], str -> str[1]));
	}
		
}
