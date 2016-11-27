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
import java.util.stream.Stream;

import org.junit.Test;

public class JoinSplitCollectionsUnitTest {

	@Test
	public void whenJoiningTwoArrays_thenJoined() {
		String[] animals1 = new String[] { "Dog", "Cat" };
		String[] animals2 = new String[] { "Bird", "Cow" };
		String[] result = Stream.concat(Arrays.stream(animals1), Arrays.stream(animals2))
				.toArray(size -> new String[size]);

		assertArrayEquals(result, new String[] { "Dog", "Cat", "Bird", "Cow" });
	}

	@Test
	public void whenJoiningTwoListsWithFilter_thenJoined() {
		List<Integer> list1 = Arrays.asList(7, 8, 11);
		List<Integer> list2 = Arrays.asList(9, 12, 10);
		List<Integer> result = Stream.concat(list1.stream(), list2.stream()).filter(next -> next <= 10)
				.collect(Collectors.toList());

		assertTrue(result.equals(Arrays.asList(7, 8, 9, 10)));
	}

	@Test
	public void whenConvertArrayToString_thenConverted() {
		String[] colors = new String[] { "Red", "Blue", "Green", "Yellow" };
		String result = Arrays.stream(colors)
				.collect(Collectors.joining(", "));

		assertEquals(result, "Red, Blue, Green, Yellow");
	}

	@Test
	public void whenConvertListToString_thenConverted() {
		List<String> directions = Arrays.asList("Left", "Right", "Top", "Bottom");
		String result = directions.stream()
				.collect(Collectors.joining(", "));

		assertEquals(result, "Left, Right, Top, Bottom");
	}

	@Test
	public void whenConvertMapToString_thenConverted() {
		Map<Integer, String> users = new HashMap<>();
		users.put(1, "John Doe");
		users.put(2, "Paul Smith");
		users.put(3, "Susan Anderson");

		String result = users.entrySet().stream().map(entry -> entry.getKey() + " = " + entry.getValue())
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
	public void whenSplitListGroupOddEven_thenConverted() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Map<Boolean, List<Integer>> result = list.stream()
				.collect(Collectors.partitioningBy(number -> number % 2 == 0));
		
		assertTrue(result.get(true).equals(Arrays.asList(2, 4, 6, 8, 10)));
		assertTrue(result.get(false).equals(Arrays.asList(1, 3, 5, 7, 9)));
	}
	
@Test
public void whenSplitArrayByWordLength_thenConverted() {
	String[] words = new String[]{"bye", "cold", "it", "and", "my", "word"};
	Map<Integer, List<String>> result = Arrays.stream(words)
			.collect(Collectors.groupingBy(word -> word.length()));
	
	assertTrue(result.get(2).equals(Arrays.asList("it", "my")));
	assertTrue(result.get(3).equals(Arrays.asList("bye", "and")));
	assertTrue(result.get(4).equals(Arrays.asList("cold", "word")));
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
