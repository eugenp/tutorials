package com.baeldung.maps.initialize;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class EmptyMapInitializer {

  public static Map<String, String> articleMap;
  static {
    articleMap = new HashMap<>();
  }

  public static Map<String, String> createEmptyMap() {
    return Collections.emptyMap();
  }

  public void createMapUsingConstructors() {
    Map hashMap = new HashMap();
    Map linkedHashMap = new LinkedHashMap();
    Map treeMap = new TreeMap();
  }

  public Map<String, String> createEmptyMapUsingMapsObject() {
    Map<String, String> emptyMap = Maps.newHashMap();
    return emptyMap;
  }

  public Map createGenericEmptyMapUsingMapsObject() {
    Map genericEmptyMap = Maps.<String, Integer>newHashMap();
    return genericEmptyMap;
  }

  public static Map<String, String> createMapUsingGuava() {
    Map<String, String> emptyMapUsingGuava =
        Maps.newHashMap(ImmutableMap.of());
    return emptyMapUsingGuava;
  }

  public SortedMap<String, String> createEmptySortedMap() {
    SortedMap<String, String> sortedMap = Collections.emptySortedMap();
    return sortedMap;
  }

  public NavigableMap<String, String> createEmptyNavigableMap() {
    NavigableMap<String, String> navigableMap = Collections.emptyNavigableMap();
    return navigableMap;
  }

}
