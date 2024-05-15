package com.baeldung.map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.Test;

public class EmptyMapInitializerUnitTest {

  @Test(expected=UnsupportedOperationException.class)
  public void givenEmptyMap_whenAddingEntries_throwsException() {
    Map<String, String> map = EmptyMapInitializer.createEmptyMap();
    map.put("key", "value");
  }

  @Test
  public void givenEmptyMap_whenChecked_returnsTrue() {
    assertTrue(EmptyMapInitializer.articleMap.isEmpty());
  }

  @Test
  public void givenEmptyMap_whenCreatedUsingGuava_returnsEmptyOrNot() {
    Map<String, String> emptyMapUsingGuava =
        EmptyMapInitializer.createMapUsingGuava();
    assertTrue(emptyMapUsingGuava.isEmpty());
    emptyMapUsingGuava.put("key", "value");
    assertFalse(emptyMapUsingGuava.isEmpty());
  }

  @Test(expected=UnsupportedOperationException.class)
  public void givenImmutableEmptyMapUsingGuava_whenAddingEntries_throwsException() {
    Map<String, String> map = EmptyMapInitializer.createImmutableMapUsingGuava();
    map.put("key", "value");
  }
}
