package com.baeldung.streams.tomapvsgroupingby;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

final class City {

    private final String name;
    private final String country;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof City) {
            City city = (City) o;
            return Objects.equals(name, city.name) && Objects.equals(country, city.country);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(country);
        return result;
    }
}

public class ToMapVsGroupingByUnitTest {

    private static final City PARIS = new City("Paris", "France");
    private static final City BERLIN = new City("Berlin", "Germany");
    private static final City TOKYO = new City("Tokyo", "Japan");

    private static final City NICE = new City("Nice", "France");
    private static final City HAMBURG = new City("Hamburg", "Germany");
    private static final City AACHEN = new City("Aachen", "Germany");

    private static final City FRANCE_NULL = new City(null, "France");

    private static final City COUNTRY_NULL = new City("Unknown", null);

    @Test
    void whenUsingToMap_thenGetExpectedResult() {
        Map<String, City> result = Stream.of(PARIS, BERLIN, TOKYO)
            .collect(Collectors.toMap(City::getCountry, Function.identity()));

        Map<String, City> expected = Map.of(
            // @formatter:off
            "France", PARIS,
            "Germany", BERLIN,
            "Japan", TOKYO
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingGroupingBy_thenGetExpectedResult() {
        Map<String, List<City>> result = Stream.of(PARIS, BERLIN, TOKYO)
            .collect(Collectors.groupingBy(City::getCountry));

        Map<String, List<City>> expected = Map.of(
            // @formatter:off
            "France", List.of(PARIS),
            "Germany", List.of(BERLIN),
            "Japan", List.of(TOKYO)
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingGroupingAndToMapWithNullKeys_thenGetExpectedResult() {

        Map<String, City> result = Stream.of(PARIS, COUNTRY_NULL)
            .collect(Collectors.toMap(City::getCountry, Function.identity()));

        Map<String, City> expected = new HashMap<>() {{
            put("France", PARIS);
            put(null, COUNTRY_NULL);
        }};
        assertEquals(expected, result);

        assertThrows(NullPointerException.class, () -> Stream.of(PARIS, COUNTRY_NULL)
            .collect(Collectors.groupingBy(City::getCountry)));
    }

    @Test
    void whenUsingToMapWithDuplicateKey_thenGetExpectedResult() {
        assertThrows(IllegalStateException.class, () -> Stream.of(PARIS, BERLIN, TOKYO, NICE, HAMBURG, AACHEN)
            .collect(Collectors.toMap(City::getCountry, Function.identity())));

        Map<String, City> result = Stream.of(PARIS, BERLIN, TOKYO, NICE, HAMBURG, AACHEN)
            .collect(Collectors.toMap(City::getCountry, Function.identity(), (c1, c2) -> c1.getName()
                .compareTo(c2.getName()) < 0 ? c1 : c2));

        Map<String, City> expected = Map.of(
            // @formatter:off
            "France", NICE, // Paris, Nice
            "Germany", AACHEN, // Berlin, Aachen, Hamburg
            "Japan", TOKYO
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingGroupingByWithDuplicateKey_thenGetExpectedResult() {
        Map<String, List<City>> result = Stream.of(PARIS, BERLIN, TOKYO, NICE, HAMBURG, AACHEN)
            .collect(Collectors.groupingBy(City::getCountry));

        Map<String, List<City>> expected = Map.of(
            // @formatter:off
            "France", List.of(PARIS, NICE),
            "Germany", List.of(BERLIN, HAMBURG, AACHEN),
            "Japan", List.of(TOKYO)
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingToMapWithValueMapper_thenGetExpectedResult() {
        Map<String, String> result = Stream.of(PARIS, BERLIN, TOKYO)
            .collect(Collectors.toMap(City::getCountry, City::getName));

        Map<String, String> expected = Map.of(
            // @formatter:off
            "France", "Paris",
            "Germany", "Berlin",
            "Japan", "Tokyo"
            // @formatter:on
        );

        assertEquals(expected, result);

        assertThrows(NullPointerException.class, () -> Stream.of(PARIS, FRANCE_NULL)
            .collect(Collectors.toMap(City::getCountry, City::getName)));
    }

    @Test
    void whenUsingGroupingByWithValueMapping_thenGetExpectedResult() {
        Map<String, List<String>> result = Stream.of(PARIS, BERLIN, TOKYO)
            .collect(Collectors.groupingBy(City::getCountry, mapping(City::getName, toList())));

        Map<String, List<String>> expected = Map.of(
            // @formatter:off
            "France", List.of("Paris"),
            "Germany", List.of("Berlin"),
            "Japan", List.of("Tokyo")
            // @formatter:on
        );

        assertEquals(expected, result);

        Map<String, List<String>> resultWithNull = Stream.of(PARIS, BERLIN, TOKYO, FRANCE_NULL)
            .collect(Collectors.groupingBy(City::getCountry, mapping(City::getName, toList())));

        Map<String, List<String>> expectedWithNull = Map.of(
            // @formatter:off
            "France", newArrayList("Paris", null),
            "Germany", newArrayList("Berlin"),
            "Japan", List.of("Tokyo")
            // @formatter:on
        );

        assertEquals(expectedWithNull, resultWithNull);
    }
}