package com.baeldung.bigdecimalequalvscompare;

import static java.math.RoundingMode.HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class BigDecimalEqualityUnitTest {

    @ParameterizedTest
    @MethodSource("decimalCompareToProvider")
    void givenBigDecimals_WhenCompare_ThenGetReasonableResult(BigDecimal fistDecimal,
      BigDecimal secondDecimal, boolean areComparablySame) {
        assertEquals(fistDecimal.compareTo(secondDecimal) == 0, areComparablySame);
    }


    @ParameterizedTest
    @MethodSource("decimalEqualsProvider")
    void givenBigDecimals_WhenCheckEquality_ThenConsiderPrecision(BigDecimal fistDecimal,
      BigDecimal secondDecimal, boolean areEqual) {
        assertEquals(fistDecimal.equals(secondDecimal), areEqual);
    }

    @ParameterizedTest
    @MethodSource("decimalEqualsProvider")
    void givenBigDecimals_WhenCheckEqualityWithoutTrailingZeros_ThenTheSameAsCompareTo(BigDecimal fistDecimal,
      BigDecimal secondDecimal) {
        BigDecimal strippedFirstDecimal = fistDecimal.stripTrailingZeros();
        BigDecimal strippedSecondDecimal = secondDecimal.stripTrailingZeros();
        assertEquals(strippedFirstDecimal.equals(strippedSecondDecimal),
          strippedFirstDecimal.compareTo(strippedSecondDecimal) == 0);
    }

    @ParameterizedTest
    @MethodSource("decimalProvider")
    void givenListOfDecimals_WhenAddingToHashSet_ThenUsingEquals(List<BigDecimal> decimalList) {
        Set<BigDecimal> decimalSet = new HashSet<>(decimalList);
        assertThat(decimalSet).hasSameElementsAs(decimalList);
    }

    @ParameterizedTest
    @MethodSource("decimalProvider")
    void givenListOfDecimals_WhenAddingToSortedSet_ThenUsingCompareTo(List<BigDecimal> decimalList,
      List<BigDecimal> expectedDecimalList) {
        Set<BigDecimal> decimalSet = new TreeSet<>(decimalList);
        assertThat(decimalSet).hasSameElementsAs(expectedDecimalList);
    }

    @ParameterizedTest
    @CsvSource({
      "2.0, 2.00",
      "4.0, 4.00"
    })
    void givenNumbersWithDifferentPrecision_WhenPerformingTheSameOperation_TheResultsAreDifferent(
      String firstNumber, String secondNumber) {

        BigDecimal firstResult = new BigDecimal(firstNumber).divide(BigDecimal.valueOf(3), HALF_UP);
        BigDecimal secondResult = new BigDecimal(secondNumber).divide(BigDecimal.valueOf(3), HALF_UP);
        assertThat(firstResult).isNotEqualTo(secondResult);
    }

    static Stream<Arguments> decimalCompareToProvider() {
        return Stream.of(
          Arguments.of(new BigDecimal("0.1"), new BigDecimal("0.1"), true),
          Arguments.of(new BigDecimal("1.1"), new BigDecimal("1.1"), true),
          Arguments.of(new BigDecimal("1.10"), new BigDecimal("1.1"), true),
          Arguments.of(new BigDecimal("0.100"), new BigDecimal("0.10000"), true),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1000"), true),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1001"), false),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1010"), false),
          Arguments.of(new BigDecimal("0.2"), new BigDecimal("0.19999999"), false),
          Arguments.of(new BigDecimal("1.0"), new BigDecimal("1.1"), false),
          Arguments.of(new BigDecimal("0.01"), new BigDecimal("0.0099999"), false)
        );
    }

    static Stream<Arguments> decimalEqualsProvider() {
        return Stream.of(
          Arguments.of(new BigDecimal("0.1"), new BigDecimal("0.1"), true),
          Arguments.of(new BigDecimal("1.1"), new BigDecimal("1.1"), true),
          Arguments.of(new BigDecimal("1.10"), new BigDecimal("1.1"), false),
          Arguments.of(new BigDecimal("0.100"), new BigDecimal("0.10000"), false),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1000"), false),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1001"), false),
          Arguments.of(new BigDecimal("0.10"), new BigDecimal("0.1010"), false),
          Arguments.of(new BigDecimal("0.2"), new BigDecimal("0.19999999"), false),
          Arguments.of(new BigDecimal("1.0"), new BigDecimal("1.1"), false),
          Arguments.of(new BigDecimal("0.01"), new BigDecimal("0.0099999"), false)
        );
    }

    static Stream<Arguments> decimalProvider() {
        return Stream.of(Arguments.of(Arrays.asList(
            new BigDecimal("1.1"),
            new BigDecimal("1.10"),
            new BigDecimal("1.100"),
            new BigDecimal("0.10"),
            new BigDecimal("0.100"),
            new BigDecimal("0.1000"),
            new BigDecimal("0.2"),
            new BigDecimal("0.20"),
            new BigDecimal("0.200")),

          Arrays.asList(
            new BigDecimal("1.1"),
            new BigDecimal("0.10"),
            new BigDecimal("0.2"))));
    }
}
