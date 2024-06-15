package com.baeldung.comparenumbers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ComparingNumbersOfDifferentClassesUnitTest {

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenSameNumbersButDifferentPrimitives_WhenCheckEquality_ThenTheyEqual(String number) {
        int integerNumber = Integer.parseInt(number);
        long longNumber = Long.parseLong(number);
        assertEquals(longNumber, integerNumber);
    }

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenSameNumbersButDifferentPrimitivesWithIntegerOverflow_WhenCheckEquality_ThenTheyNotEqual(String number) {
        int integerNumber = Integer.MAX_VALUE + Integer.parseInt(number);
        long longNumber = Integer.MAX_VALUE + Long.parseLong(number);
        assertNotEquals(longNumber, integerNumber);
    }

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenSameNumbersButDifferentPrimitivesTypes_WhenCheckEquality_ThenTheyEqual(String number) {
        int integerNumber = Integer.parseInt(number);
        double doubleNumber = Double.parseDouble(number);
        assertEquals(doubleNumber, integerNumber);
    }

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenDifferentNumbersButDifferentPrimitivesTypes_WhenCheckEquality_ThenTheyNotEqual(String number) {
        int integerNumber = Integer.parseInt(number);
        double doubleNumber = Double.parseDouble(number) + 0.0000000000001;
        assertNotEquals(doubleNumber, integerNumber);
    }

    @Test
    void givenSameNumbersButDifferentPrimitivesWithLongOverflow_WhenCheckEquality_ThenTheyEqual() {
        long longValue = BigInteger.valueOf(Long.MAX_VALUE)
          .add(BigInteger.ONE)
          .multiply(BigInteger.TWO).longValue();
        int integerValue = BigInteger.valueOf(Long.MAX_VALUE)
          .add(BigInteger.ONE).intValue();
        assertThat(longValue).isEqualTo(integerValue);
    }
    @Test
    void givenSameNumbersButDifferentPrimitivesWithDoubleOverflow_WhenCheckEquality_ThenTheyEqual() {
        double firstDoubleValue = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.valueOf(42)).doubleValue();
        double secondDoubleValue = BigDecimal.valueOf(Double.MAX_VALUE).doubleValue();
        assertEquals(firstDoubleValue, secondDoubleValue);
    }

    @Test
    void givenSameNumbersWithDoubleRoundingErrors_WhenCheckEquality_ThenTheyNotEqual() {
        double doubleValue = 0.3 / 0.1;
        int integerValue = 30 / 10;
        assertNotEquals(doubleValue, integerValue);
    }

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenSameNumbersButDifferentWrappers_WhenCheckEquality_ThenTheyNotEqual(String number) {
        Integer integerNumber = Integer.valueOf(number);
        Long longNumber = Long.valueOf(number);
        assertNotEquals(longNumber, integerNumber);
    }

    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @ParameterizedTest
    void givenSameNumbersButWrapperTypes_WhenCheckEquality_ThenTheyNotEqual(String number) {
        Float floatNumber = Float.valueOf(number);
        Integer integerNumber = Integer.valueOf(number);
        assertNotEquals(floatNumber, integerNumber);
    }

    @MethodSource("numbersWithDifferentScaleProvider")
    @ParameterizedTest
    void givenBigDecimalsWithDifferentScale_WhenCheckEquality_ThenTheyNotEqual(String firstNumber,
      String secondNumber) {
        BigDecimal firstBigDecimal = new BigDecimal(firstNumber);
        BigDecimal secondBigDecimal = new BigDecimal(secondNumber);

        assertNotEquals(firstBigDecimal, secondBigDecimal);
    }

    @MethodSource("numbersWithDifferentScaleProvider")
    @ParameterizedTest
    void givenBigDecimalsWithDifferentScale_WhenCompare_ThenTheyEqual(String firstNumber,
      String secondNumber) {
        BigDecimal firstBigDecimal = new BigDecimal(firstNumber);
        BigDecimal secondBigDecimal = new BigDecimal(secondNumber);

        assertEquals(0, firstBigDecimal.compareTo(secondBigDecimal));
    }

    @MethodSource("numbersWithDifferentScaleProvider")
    @ParameterizedTest
    void givenBigDecimalsWithDifferentScale_WhenCompareWithAssertJ_ThenTheyEqual(String firstNumber,
      String secondNumber) {
        BigDecimal firstBigDecimal = new BigDecimal(firstNumber);
        BigDecimal secondBigDecimal = new BigDecimal(secondNumber);

        assertThat(firstBigDecimal).isEqualByComparingTo(secondBigDecimal);
    }


    @MethodSource("numbersWithSameScaleProvider")
    @ParameterizedTest
    void givenBigDecimalsWithSameScale_WhenCheckEquality_ThenTheyEqual(String firstNumber,
      String secondNumber) {
        BigDecimal firstBigDecimal = new BigDecimal(firstNumber);
        BigDecimal secondBigDecimal = new BigDecimal(secondNumber);

        assertEquals(firstBigDecimal, secondBigDecimal);
    }

    @MethodSource("numbersWithSameScaleProvider")
    @ParameterizedTest
    void givenBigDecimalsWithSameScale_WhenCompare_ThenTheyEqual(String firstNumber,
      String secondNumber) {
        BigDecimal firstBigDecimal = new BigDecimal(firstNumber);
        BigDecimal secondBigDecimal = new BigDecimal(secondNumber);

        assertEquals(0, firstBigDecimal.compareTo(secondBigDecimal));
    }


    static Stream<Arguments> numbersWithDifferentScaleProvider() {
        return Stream.of(
          Arguments.of("0", "0.0"), Arguments.of("1", "1.0"),
          Arguments.of("2", "2.0"), Arguments.of("3", "3.0"),
          Arguments.of("4", "4.0"), Arguments.of("5", "5.0"),
          Arguments.of("6", "6.0"), Arguments.of("7", "7.0")
        );
    }

    static Stream<Arguments> numbersWithSameScaleProvider() {
        return Stream.of(
          Arguments.of("0", "0"), Arguments.of("1", "1"),
          Arguments.of("2", "2"), Arguments.of("3", "3"),
          Arguments.of("4", "4"), Arguments.of("5", "5"),
          Arguments.of("6", "6"), Arguments.of("7", "7")
        );
    }
}
