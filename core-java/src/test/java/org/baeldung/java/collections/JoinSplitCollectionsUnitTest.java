package org.baeldung.java.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
	public void whenJoiningTwoCollections_thenJoined() {
		Collection<Integer> collection1 = Arrays.asList(7, 8, 9);
		Collection<Integer> collection2 = Arrays.asList(10, 11, 12);
		Collection<Integer> result = Stream.concat(collection1.stream(), collection2.stream())
				.collect(Collectors.toList());
	
		assertTrue(result.equals(Arrays.asList(7, 8, 9, 10, 11, 12)));
	}

	@Test
	public void whenJoiningTwoCollectionsWithFilter_thenJoined() {
		Collection<Integer> collection1 = Arrays.asList(7, 8, 11);
		Collection<Integer> collection2 = Arrays.asList(9, 12, 10);
		Collection<Integer> result = Stream.concat(collection1.stream(), collection2.stream())
				.filter(next -> next <= 10)
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
	public void whenConvertCollectionToString_thenConverted() {
		Collection<String> directions = Arrays.asList("Left", "Right", "Top", "Bottom");
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
	public void whenConvertNestedCollectionToString_thenConverted() {
		Collection<List<String>> nested = new ArrayList<>();
		nested.add(Arrays.asList("Left", "Right", "Top", "Bottom"));
		nested.add(Arrays.asList("Red", "Blue", "Green", "Yellow"));
	
		String result = nested.stream()
				.map(nextList -> nextList.stream()
						.collect(Collectors.joining("-")))
				.collect(Collectors.joining("; "));
	
		assertEquals(result, "Left-Right-Top-Bottom; Red-Blue-Green-Yellow");
	}

	@Test
	public void whenConvertCollectionToStringAndSkipNull_thenConverted() {
		Collection<String> fruits = Arrays.asList("Apple", "Orange", null, "Grape");
		String result = fruits.stream()
				.filter(next -> next != null)
				.collect(Collectors.joining(", "));
	
		assertEquals(result, "Apple, Orange, Grape");
	}

	@Test
	public void whenSplitCollectionHalf_thenConverted() {
		Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Collection<Integer> result1 = new ArrayList<>();
		Collection<Integer> result2 = new ArrayList<>();
		AtomicInteger count = new AtomicInteger();
		int midpoint = Math.round(numbers.size() / 2);
		
		numbers.forEach(next -> {
			int index = count.getAndIncrement();
			if(index < midpoint){
				result1.add(next);
			}else{
				result2.add(next);
			}
		});
		
		assertTrue(result1.equals(Arrays.asList(1, 2, 3, 4, 5)));
		assertTrue(result2.equals(Arrays.asList(6, 7, 8, 9, 10)));
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
	public void whenConvertStringToCollection_thenConverted() {
		String colors = "Left, Right, Top, Bottom";
		Collection<String> result = Arrays.asList(colors.split(", "));

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
	public void whenConvertCollectionToStringMultipleSeparators_thenConverted() {
		String fruits = "Apple. , Orange, Grape. Lemon";

		Collection<String> result = Arrays.stream(fruits.split("[,|.]"))
				.map(String::trim).filter(next -> !next.isEmpty())
				.collect(Collectors.toList());

		assertTrue(result.equals(Arrays.asList("Apple", "Orange", "Grape", "Lemon")));
	}
}
