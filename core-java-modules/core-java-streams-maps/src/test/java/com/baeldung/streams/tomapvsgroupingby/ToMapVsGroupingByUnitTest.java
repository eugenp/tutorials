package com.baeldung.streams.tomapvsgroupingby;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class City {

    private String name;
    private String country;

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
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City city)) {
            return false;
        }

        return Objects.equals(name, city.name) && Objects.equals(country, city.country);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(country);
        return result;
    }
}

public class ToMapVsGroupingByUnitTest {

    private City paris = new City("Paris", "France");
    private City berlin = new City("Berlin", "Germany");
    private City london = new City("London", "UK");
    private City newYork = new City("New York", "USA");
    private City tokyo = new City("Tokyo", "Japan");

    private City nice = new City("Nice", "France");
    private City hamburg = new City("Hamburg", "Germany");
    private City aachen = new City("Aachen", "Germany");

    private City franceNull = new City(null, "France");
    private City germanyNull = new City(null, "Germany");

    @Test
    void whenUsingToMap_thenGetExpectedResult() {
        Map<String, City> result = Stream.of(paris, berlin, london, newYork, tokyo)
            .collect(Collectors.toMap(City::getCountry, Function.identity()));

        Map<String, City> expected = Map.of(
            // @formatter:off
            "France", paris,
            "Germany", berlin,
            "UK", london,
            "USA", newYork,
            "Japan", tokyo
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingGroupingBy_thenGetExpectedResult() {
        Map<String, List<City>> result = Stream.of(paris, berlin, london, newYork, tokyo)
            .collect(Collectors.groupingBy(City::getCountry));

        Map<String, List<City>> expected = Map.of(
            // @formatter:off
            "France", List.of(paris),
            "Germany", List.of(berlin),
            "UK", List.of(london),
            "USA", List.of(newYork),
            "Japan", List.of(tokyo)
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingToMapWithDuplicateKey_thenGetExpectedResult() {
        assertThrows(IllegalStateException.class, () -> Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen)
            .collect(Collectors.toMap(City::getCountry, Function.identity())));

        Map<String, City> result = Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen)
            .collect(Collectors.toMap(City::getCountry, Function.identity(), (c1, c2) -> c1.getName()
                .compareTo(c2.getName()) < 0 ? c1 : c2));

        Map<String, City> expected = Map.of(
            // @formatter:off
            "France", nice, // Paris, Nice
            "Germany", aachen, // Berlin, Aachen, Hamburg
            "UK", london,
            "USA", newYork,
            "Japan", tokyo
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingGroupingByWithDuplicateKey_thenGetExpectedResult() {
        Map<String, List<City>> result = Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen)
            .collect(Collectors.groupingBy(City::getCountry));

        Map<String, List<City>> expected = Map.of(
            // @formatter:off
            "France", List.of(paris, nice),
            "Germany", List.of(berlin, hamburg, aachen),
            "UK", List.of(london),
            "USA", List.of(newYork),
            "Japan", List.of(tokyo)
            // @formatter:on
        );

        assertEquals(expected, result);
    }

    @Test
    void whenUsingToMapWithValueMapper_thenGetExpectedResult() {
        Map<String, String> result = Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen)
            .collect(Collectors.toMap(City::getCountry, City::getName, (name1, name2) -> name1.compareTo(name2) < 0 ? name1 : name2));

        Map<String, String> expected = Map.of(
            // @formatter:off
            "France", "Nice",
            "Germany", "Aachen",
            "UK", "London",
            "USA", "New York",
            "Japan", "Tokyo"
            // @formatter:on
        );

        assertEquals(expected, result);

        assertThrows(NullPointerException.class, () -> Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen, franceNull, germanyNull)
            .collect(Collectors.toMap(City::getCountry, City::getName, (name1, name2) -> name1.compareTo(name2) < 0 ? name1 : name2)));
    }

    @Test
    void whenUsingGroupingByWithValueMapping_thenGetExpectedResult() {
        Map<String, List<String>> result = Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen)
            .collect(Collectors.groupingBy(City::getCountry, mapping(City::getName, toList())));

        Map<String, List<String>> expected = Map.of(
            // @formatter:off
            "France", List.of("Paris", "Nice"),
            "Germany", List.of("Berlin", "Hamburg", "Aachen"),
            "UK", List.of("London"),
            "USA", List.of("New York"),
            "Japan", List.of("Tokyo")
            // @formatter:on
        );

        assertEquals(expected, result);

        Map<String, List<String>> resultWithNull = Stream.of(paris, berlin, london, newYork, tokyo, nice, hamburg, aachen, franceNull, germanyNull)
            .collect(Collectors.groupingBy(City::getCountry, mapping(City::getName, toList())));

        Map<String, List<String>> expectedWithNull = Map.of(
            // @formatter:off
            "France", newArrayList("Paris", "Nice", null),
            "Germany", newArrayList("Berlin", "Hamburg", "Aachen", null),
            "UK", List.of("London"),
            "USA", List.of("New York"),
            "Japan", List.of("Tokyo")
            // @formatter:on
        );

        assertEquals(expectedWithNull, resultWithNull);
    }
}