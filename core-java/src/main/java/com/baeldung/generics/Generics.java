package com.baeldung.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Generics {

	// definition of a generic method
	public static <T> List<T> fromArrayToList(T[] a) {
		return Arrays.stream(a).collect(Collectors.toList());
	}

	// example of a generic method that has Number as an upper bound for T
	public static <T extends Number> List<T> fromArrayToListWithUpperBound(T[] a) {
		return Arrays.stream(a).collect(Collectors.toList());
	}

	// example of a generic method with a wild card, this method can be used
	// with a list of any subtype of Building
	public static boolean paintAllBuildings(List<? extends Building> buildings) {
		buildings.forEach(Building::paint);
		return true;
	}

}