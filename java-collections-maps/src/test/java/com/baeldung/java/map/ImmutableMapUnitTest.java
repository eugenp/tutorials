package com.baeldung.java.map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;


public class ImmutableMapUnitTest {

	@Test
	public void whenCollectionsUnModifiableMapMethod_thenOriginalCollectionChangesReflectInUnmodifiableMap() {

		Map<String, String> mutableMap = new HashMap<>();
		mutableMap.put("USA", "North America");

		Map<String, String> unmodifiableMap = Collections.unmodifiableMap(mutableMap);
		assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("Canada", "North America"));
		
		mutableMap.remove("USA");
		assertFalse(unmodifiableMap.containsKey("USA"));
		
		mutableMap.put("Mexico", "North America");
		assertTrue(unmodifiableMap.containsKey("Mexico"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void whenGuavaImmutableMapFromCopyOfMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

		Map<String, String> mutableMap = new HashMap<>();
		mutableMap.put("USA", "North America");

		ImmutableMap<String, String> immutableMap = ImmutableMap.copyOf(mutableMap);
		assertTrue(immutableMap.containsKey("USA"));
		
		assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));
		
		mutableMap.remove("USA");
		assertTrue(immutableMap.containsKey("USA"));
		
		mutableMap.put("Mexico", "North America");
		assertFalse(immutableMap.containsKey("Mexico"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void whenGuavaImmutableMapFromBuilderMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

		Map<String, String> mutableMap = new HashMap<>();
		mutableMap.put("USA", "North America");

		ImmutableMap<String, String> immutableMap = ImmutableMap.<String, String>builder()
				.putAll(mutableMap)
				.put("Costa Rica", "North America")
				.build();
		assertTrue(immutableMap.containsKey("USA"));
		assertTrue(immutableMap.containsKey("Costa Rica"));
		
		assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));
		
		mutableMap.remove("USA");
		assertTrue(immutableMap.containsKey("USA"));
		
		mutableMap.put("Mexico", "North America");
		assertFalse(immutableMap.containsKey("Mexico"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void whenGuavaImmutableMapFromOfMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

		ImmutableMap<String, String> immutableMap = ImmutableMap.of("USA", "North America", "Costa Rica", "North America");
		assertTrue(immutableMap.containsKey("USA"));
		assertTrue(immutableMap.containsKey("Costa Rica"));

		assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));
	}
	
}
