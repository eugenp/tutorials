package com.baeldung.parsedouble;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.primitives.Doubles;

import io.vavr.control.Try;

public class StringToDoubleParserUnitTest {

    @Test
    public void givenNullValue_whenParseStringToDouble_thenDefaultNaNValueIsReturned() {
        assertThat(parseStringToDouble(null)).isNaN();
    }

    @Test
    public void givenEmptyStringValue_whenParseStringToDouble_thenDefaultNaNValueIsReturned() {
        assertThat(parseStringToDouble("")).isNaN();
    }

    @Test
    public void givenStringValue_whenParseStringToDouble_thenDoubleValueIsReturned() {
        assertThat(parseStringToDouble("1")).isEqualTo(1.0d);
    }

    @Test
    public void givenStringValue_whenParseStringToDoubleWithDefault_thenDoubleValueIsReturned() {
        assertThat(parseStringToDouble("1", 2.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenEmptyStringValue_whenParseStringToDoubleWithDefault_thenDefaultValueIsReturned() {
        assertThat(parseStringToDouble("", 1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenNullValue_whenParseStringToDoubleWithDefault_thenDefaultValueIsReturned() {
        assertThat(parseStringToDouble(null, 1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenStringValue_whenParseStringToOptionalDouble_thenOptionalValueIsReturned() {
        assertThat(parseStringToOptionalDouble("1")).isEqualTo(Optional.of(1.0d));
    }

    @Test
    public void givenNullValue_whenParseStringToOptionalDouble_thenOptionalValueIsEmpty() {
        assertThat(parseStringToOptionalDouble(null)).isEqualTo(Optional.empty());
    }

    @Test
    public void givenEmptyStringValue_whenParseStringToOptionalDouble_thenOptionalValueIsEmpty() {
        assertThat(parseStringToOptionalDouble("")).isEqualTo(Optional.empty());
    }

    @Test
    public void givenEmptyStringValue_whenParseStringToOptionalDouble_thenDefaulOptionalValueIsReturned() {
        assertThat(parseStringToOptionalDouble("").orElse(1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenNullValue_whenParseStringToOptionalDouble_thenDefaulOptionalValueIsReturned() {
        assertThat(parseStringToOptionalDouble(null).orElse(1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenStringValue_whenTryStringToDouble_thenDoubleValueIsReturned() {
        assertThat(tryStringToDouble("1", 2.0d)).isEqualTo(1.0d);
    }

    @Test
    public void givenNullValue_whenTryStringToDoubleWithDefault_thenDoubleValueIsReturned() {
        assertThat(tryStringToDouble(null, 2.0d)).isEqualTo(2.0d);
    }

    @Test
    public void givenEmptyStringValue_whenTryStringToDoubleWithDefault_thenDoubleValueIsReturned() {
        assertThat(tryStringToDouble("", 2.0d)).isEqualTo(2.0d);
    }

    @Test
    public void givenTwoStringValues_whenTryParseFirstNonNull_thenDoubleValueIsReturned() {
        assertThat(Doubles.tryParse(MoreObjects.firstNonNull("1.0", "2.0"))).isEqualTo(1.0d);
    }

    @Test
    public void givenNullStringValue_whenTryParseFirstNonNull_thenSecondDoubleValueIsReturned() {
        assertThat(Doubles.tryParse(MoreObjects.firstNonNull(null, "2.0"))).isEqualTo(2.0d);
    }

    @Test
    public void givenEmptyStringValue_whenTryParseFirstNonNull_thenNullIsReturned() {
        assertThat(Doubles.tryParse(MoreObjects.firstNonNull("", "2.0"))).isEqualTo(null);
    }

    @Test
    public void givenStringValue_whenToDouble_thenDoubleValueIsReturned() {
        assertThat(NumberUtils.toDouble("1.0")).isEqualTo(1.0d);
    }

    @Test
    public void givenNullValue_whenToDouble_thenLibraryDefaultDoubleValueIsReturned() {
        String nullString = null;
        assertThat(NumberUtils.toDouble(nullString)).isEqualTo(0.0d);
    }

    @Test
    public void givenEmptyStringValue_whenToDouble_thenLibraryDefaultDoubleValueIsReturned() {
        assertThat(NumberUtils.toDouble("")).isEqualTo(0.0d);
    }

    @Test
    public void givenEmptyStringValue_whenToDoubleWithDefault_thenDoubleValueIsReturned() {
        assertThat(NumberUtils.toDouble("", 2.0d)).isEqualTo(2.0d);
    }

    @Test
    public void givenNullValue_whenToDoubleWithDefault_thenDoubleValueIsReturned() {
        String nullString = null;
        assertThat(NumberUtils.toDouble(nullString, 2.0d)).isEqualTo(2.0d);
    }

    @Test
    public void givenStringValue_whenToDoubleWithDefault_thenDoubleValueIsReturned() {
        assertThat(NumberUtils.toDouble("1.0", 2.0d)).isEqualTo(1.0d);
    }

    private static Optional<Double> parseStringToOptionalDouble(String value) {
        return value == null || value.isEmpty() ? Optional.empty() : Optional.of(Double.valueOf(value));
    }

    private static double parseStringToDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    private static double parseStringToDouble(String value, double defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : Double.parseDouble(value);
    }

    private static double tryStringToDouble(String value, double defaultValue) {
        return Try.of(() -> Double.parseDouble(value)).getOrElse(defaultValue);
    }
}
