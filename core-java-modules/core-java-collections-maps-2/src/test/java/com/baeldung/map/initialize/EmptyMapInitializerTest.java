package com.baeldung.map.initialize;

import java.util.Map;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyMapInitializerTest {

  @Test(expected=UnsupportedOperationException.class)
  public void givenEmptyMap_whenAddingEntries_throwsException() {
    Map<String, String> map = EmptyMapInitializer.createEmptyMap();
    map.put("key", "value");
  }

  @Test
  public void checkStaticMap_isEmpty() {
    assertTrue(EmptyMapInitializer.articleMap.isEmpty());
  }

  @Test
  public void emptyMapCreated_usingGuava() {
    Map<String, String> emptyMapUsingGuava = EmptyMapInitializer.createMapUsingGuava();
    assertTrue(emptyMapUsingGuava.isEmpty());
    emptyMapUsingGuava.put("key", "value");
    assertFalse(emptyMapUsingGuava.isEmpty());
  }

}
