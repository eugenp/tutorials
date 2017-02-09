package com.baeldung.streamApi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinerSplitter {

	public static String join ( String[] arrayOfString ) {
		return Arrays.asList(arrayOfString)
				.stream()
				.map(x -> x)
				.collect(Collectors.joining(","));
	}
	
	public static List<String> split ( String str ) {
		return Stream.of(str.split(","))
				.map (elem -> new String(elem))
				.collect(Collectors.toList());
	}
	
}
