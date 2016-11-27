package org.baeldung.java.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectionsToAndFromStringUnitTest {
	@Test
	public void whenConvertArrayToString_thenConverted() {
		String[] colors = new String[] { "Red", "Blue", "Green", "Yellow" };
		String result = Arrays.stream(colors).collect(Collectors.joining(", "));

		assertEquals(result, "Red, Blue, Green, Yellow");
	}

	@Test
	public void whenConvertListToString_thenConverted() {
		List<String> directions = Arrays.asList("Left", "Right", "Top", "Bottom");
		String result = directions.stream().collect(Collectors.joining(", "));

		assertEquals(result, "Left, Right, Top, Bottom");
	}

	@Test
	public void whenConvertMapToString_thenConverted() {
		Map<Integer, String> users = new HashMap<>();
		users.put(1, "John Doe");
		users.put(2, "Paul Smith");
		users.put(3, "Susan Anderson");

		String result = users.entrySet().stream()
				.map(entry -> entry.getKey() + " = " + entry.getValue())
				.collect(Collectors.joining(", "));

		assertEquals(result, "1 = John Doe, 2 = Paul Smith, 3 = Susan Anderson");
	}

	@Test
	public void whenConvertNestedListToString_thenConverted() {
		List<List<String>> nested = new ArrayList<>();
		nested.add(Arrays.asList("Left", "Right", "Top", "Bottom"));
		nested.add(Arrays.asList("Red", "Blue", "Green", "Yellow"));

		String result = nested.stream()
				.map(nextList -> nextList.stream()
						.collect(Collectors.joining("-")))
				.collect(Collectors.joining("; "));

		assertEquals(result, "Left-Right-Top-Bottom; Red-Blue-Green-Yellow");
	}

	@Test
	public void whenConvertListToStringAndSkipNull_thenConverted() {
		List<String> fruits = Arrays.asList("Apple", "Orange", null, "Grape");
		String result = fruits.stream()
				.filter(next -> next != null)
				.collect(Collectors.joining(", "));

		assertEquals(result, "Apple, Orange, Grape");
	}

	@Test
	public void whenConvertStringToArray_thenConverted() {
		String colors = "Red, Blue, Green, Yellow";
		String[] result = colors.split(", ");

		assertArrayEquals(result, new String[] { "Red", "Blue", "Green", "Yellow" });
	}

	@Test
	public void whenConvertStringToList_thenConverted() {
		String colors = "Left, Right, Top, Bottom";
		List<String> result = Arrays.asList(colors.split(", "));

		assertTrue(result.equals(Arrays.asList("Left", "Right", "Top", "Bottom")));
	}

	@Test
	public void whenConvertStringToMap_thenConverted() {
		String users = "1 = John Doe, 2 = Paul Smith, 3 = Susan Anderson";

		Map<Integer, String> result = Arrays.stream(users.split(", "))
				.map(next -> next.split(" = "))
				.collect(Collectors.toMap(entry -> Integer.parseInt(entry[0]), entry -> entry[1]));

		assertEquals(result.get(1), "John Doe");
		assertEquals(result.get(2), "Paul Smith");
		assertEquals(result.get(3), "Susan Anderson");
	}

	@Test
	public void whenConvertListToStringMultipleSeparators_thenConverted() {
		String fruits = "Apple. , Orange, Grape. Lemon";

		List<String> result = Arrays.stream(fruits.split("[,|.]"))
				.map(String::trim).filter(next -> !next.isEmpty())
				.collect(Collectors.toList());

		assertTrue(result.equals(Arrays.asList("Apple", "Orange", "Grape", "Lemon")));
	}
}
