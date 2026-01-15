package com.baeldung.stablevalues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StableValuesUnitTest {

    private Set<String> cities;

    private String expensiveMethodToDetermineCountry(String city) {
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
    void givenStableFunctionForCityToCountry_whenValidInputsUsed_thenVerifyFunctionResultsAreExpected() {
        Function<String, String> cityToCountry = StableValue.function(cities, city -> expensiveMethodToDetermineCountry(city));
        
        assertThat(cityToCountry.apply("London")).isEqualTo("England");
        assertThat(cityToCountry.apply("Madrid")).isEqualTo("Spain");
        assertThat(cityToCountry.apply("Paris")).isEqualTo("France");
    }

    @Test
    void givenStableFunctionForCityToCountry_whenInvalidInputUsed_thenExceptionThrown() {
        Function<String, String> cityToCountry = StableValue.function(cities, city -> expensiveMethodToDetermineCountry(city));
        
        assertThatIllegalArgumentException().isThrownBy(() -> cityToCountry.apply("Berlin"));
    }

    @Test
    void givenStableListForFiveTimesTable_thenVerifyElementsAreExpected() {
        List<Integer> fiveTimesTable = StableValue.list(11, index -> index * 5);
        
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
    void givenStableMapForCityToCountry_thenVerifyValuesAreExpected() {
        Map<String, String> cityToCountry = StableValue.map(cities, city -> expensiveMethodToDetermineCountry(city));
        
        assertThat(cityToCountry.get("London")).isEqualTo("England");
        assertThat(cityToCountry.get("Madrid")).isEqualTo("Spain");
        assertThat(cityToCountry.get("Paris")).isEqualTo("France");
    }

    @BeforeEach
    void init() {
        cities = Set.of("London", "Madrid", "Paris");
    }
}
