package com.baeldung.junit5.parameterized;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BigCountriesTest {

    @Order(1)
    @DisplayName("Big Countries - Simple")
    @ParameterizedTest(name = "[{arguments}]: ''{0}'' with population ''{1}'' people")
    @FieldSource("simpleArguments")
    public void givenBigCountryData_usingCountryUtil_isBigCountry_shouldReturnTrue_simple(String name, long population) {
        Country country = new Country(name, population);

        boolean isBigCountry = CountryUtil.isBigCountry(country);

        assertTrue(isBigCountry, "The country provided is not considered big!");
    }

    private static List<Arguments> simpleArguments = Arrays.asList(
            arguments("India", 1_450_935_791),
            arguments("China", 1_419_321_278),
            arguments("United States", 345_426_571)
    );

    @Order(2)
    @DisplayName("Big Countries - With Named Arguments")
    @ParameterizedTest(name = "{argumentsWithNames}")
    @FieldSource("namedArguments")
    public void givenBigCountryData_usingCountryUtil_isBigCountry_shouldReturnTrue_namedArguments(String countryName, long countryPopulation) {
        Country country = new Country(countryName, countryPopulation);

        boolean isBigCountry = CountryUtil.isBigCountry(country);

        assertTrue(isBigCountry, "The country provided is not considered big!");
    }

    private static List<Arguments> namedArguments = Arrays.asList(
            arguments(named("Most populated country in Asia", "India"), 1_450_935_791),
            arguments("China", 1_419_321_278),
            arguments(named("Biggest country in America", "United States"), 345_426_571)
    );

    @Order(3)
    @DisplayName("Big Countries - Named Set")
    @ParameterizedTest(name = "''{0}'' is considered {argumentSetName} due to his population of ''{1}'' people")
    @FieldSource("argumentSets")
    public void notablePeople_withSetName(String name, long population) {
        Country country = new Country(name, population);

        boolean isBigCountry = CountryUtil.isBigCountry(country);

        assertTrue(isBigCountry, "The country provided is not considered big!");
    }

    private static List<Arguments> argumentSets = Arrays.asList(
            argumentSet("the most populated country in Asia", "India", 1_450_935_791),
            argumentSet("the second most populated country in Asia", "China", 1_419_321_278),
            argumentSet("biggest country in America", "United States", 345_426_571)
    );

}
