package com.baeldung.streams.tomap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class City {
    private String name;
    private String locatedIn;

    public City(String name, String locatedIn) {
        this.name = name;
        this.locatedIn = locatedIn;
    }

    public String getName() {
        return name;
    }

    public String getLocatedIn() {
        return locatedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof City))
            return false;

        City city = (City) o;

        if (!name.equals(city.name))
            return false;
        return locatedIn.equals(city.locatedIn);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + locatedIn.hashCode();
        return result;
    }
}

public class StreamToMapDuplicatedKeysHandlingUnitTest {
    // @formatter:off
    private final List<City> CITY_INPUT = Arrays.asList(
      new City("New York City", "USA"),
      new City("Shanghai", "China"),
      new City("Hamburg", "Germany"),
      new City("Paris", "France"),
      new City("Paris", "Texas, USA"));
    // @formatter:on

    @Test
    void givenCityList_whenUsingGroupingBy_shouldContainExpectedCity() {
        Map<String, List<City>> resultMap = CITY_INPUT.stream()
          .collect(groupingBy(City::getName));
        assertEquals(4, resultMap.size());
        // @formatter:off
        assertEquals(Arrays.asList(
          new City("Paris", "France"),
          new City("Paris", "Texas, USA")), resultMap.get("Paris"));
        // @formatter:on

    }

    @Test
    void givenCityList_whenContainingDuplicatedNamesUsingToMap_shouldContainExpectedCity() {
        Map<String, City> resultMap1 = CITY_INPUT.stream()
          .collect(toMap(City::getName, Function.identity(), (first, second) -> first));
        assertEquals(4, resultMap1.size());
        assertEquals(new City("Paris", "France"), resultMap1.get("Paris"));

        Map<String, City> resultMap2 = CITY_INPUT.stream()
          .collect(toMap(City::getName, Function.identity(), (first, second) -> second));
        assertEquals(4, resultMap2.size());
        assertEquals(new City("Paris", "Texas, USA"), resultMap2.get("Paris"));

        Map<String, City> resultMap3 = CITY_INPUT.stream()
          .collect(toMap(City::getName, Function.identity(), (first, second) -> {
              String locations = first.getLocatedIn() + " and " + second.getLocatedIn();
              return new City(first.getName(), locations);
          }));
        assertEquals(4, resultMap2.size());
        assertEquals(new City("Paris", "France and Texas, USA"), resultMap3.get("Paris"));
    }

}