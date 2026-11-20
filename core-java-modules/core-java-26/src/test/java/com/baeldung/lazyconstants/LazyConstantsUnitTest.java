package com.baeldung.lazyconstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LazyConstantsUnitTest {
    
    private Set<String> cities;

    private String expensiveMethodToGetCountry(String city) {
        switch(city) {
        case "Berlin":
            return "Germany";
        case "London":
            return "England";
        case "Madrid":
            return "Spain";
        case "Paris":
            return "France";
        default:
            throw new RuntimeException("Unsupported city");
        }
    }

    @Test
    void givenLazyListForFiveTimesTable_thenVerifyElementsAreExpected() {
        List<Integer> fiveTimesTable = List.ofLazy(11, index -> index * 5);

        assertThat(fiveTimesTable.get(0)).isEqualTo(0);
        assertThat(fiveTimesTable.get(1)).isEqualTo(5);
        assertThat(fiveTimesTable.get(2)).isEqualTo(10);
        assertThat(fiveTimesTable.get(3)).isEqualTo(15);
        assertThat(fiveTimesTable.get(4)).isEqualTo(20);
        assertThat(fiveTimesTable.get(5)).isEqualTo(25);
        assertThat(fiveTimesTable.get(6)).isEqualTo(30);
        assertThat(fiveTimesTable.get(7)).isEqualTo(35);
        assertThat(fiveTimesTable.get(8)).isEqualTo(40);
        assertThat(fiveTimesTable.get(9)).isEqualTo(45);
        assertThat(fiveTimesTable.get(10)).isEqualTo(50);
    }

    @Test
    void givenLazyMapForCityToCountry_thenVerifyValuesAreExpected() {
        Map<String, String> cityToCountry = Map.ofLazy(cities, city -> expensiveMethodToGetCountry(city));

        assertThat(cityToCountry.get("London")).isEqualTo("England");
        assertThat(cityToCountry.get("Madrid")).isEqualTo("Spain");
        assertThat(cityToCountry.get("Paris")).isEqualTo("France");
    }

    @BeforeEach
    void init() {
        cities = Set.of("London", "Madrid", "Paris");
    }
}