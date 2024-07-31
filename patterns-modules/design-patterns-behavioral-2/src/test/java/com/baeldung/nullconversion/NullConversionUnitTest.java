package com.baeldung.nullconversion;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.google.common.base.MoreObjects;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class NullConversionUnitTest {

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenIfWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = defaultValue;
        if (givenValue != null) {
            actual = givenValue;
        }
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsSupplierProvider.class)
    void givenIfWhenNotNullThenReturnsDefault(String givenValue, String expected,
      Supplier<String> expensiveSupplier) {
        String actual;
        if (givenValue != null) {
            actual = givenValue;
        } else {
            actual = expensiveSupplier.get();
        }
        assertDefaultConversion(givenValue, expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenTernaryWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = givenValue != null ? givenValue : defaultValue;
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsSupplierProvider.class)
    void givenLazyTernaryWhenNotNullThenReturnsDefault(String givenValue, String expected,
      Supplier<String> expensiveSupplier) {
        String actual = givenValue != null ? givenValue : expensiveSupplier.get();
        assertDefaultConversion(givenValue, expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenObjectsWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = requireNonNullElse(givenValue, defaultValue);
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsSupplierProvider.class)
    void givenLazyObjectsWhenNotNullThenReturnsDefault(String givenValue, String expected,
      Supplier<String> expensiveSupplier) {
        String actual = requireNonNullElseGet(givenValue, expensiveSupplier);
        assertDefaultConversion(givenValue, expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenOptionalWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = Optional.ofNullable(givenValue).orElse(defaultValue);
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsSupplierProvider.class)
    void givenLazyOptionalWhenNotNullThenReturnsDefault(String givenValue, String expected,
      Supplier<String> expensiveSupplier) {
        String actual = Optional.ofNullable(givenValue).orElseGet(expensiveSupplier);
        assertDefaultConversion(givenValue, expected, actual);
    }


    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenGuavaWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = MoreObjects.firstNonNull(givenValue, defaultValue);
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenGuavaOptionalWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = com.google.common.base.Optional.fromNullable(givenValue).or(defaultValue);
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsProvider.class)
    void givenApacheWhenNotNullThenReturnsDefault(String givenValue, String defaultValue) {
        String actual = ObjectUtils.firstNonNull(givenValue, defaultValue);
        assertDefaultConversion(givenValue, defaultValue, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ObjectsSupplierProvider.class)
    void givenLazyApacheWhenNotNullThenReturnsDefault(String givenValue, String expected,
      Supplier<String> expensiveSupplier) {
        String actual = ObjectUtils.getFirstNonNull(() -> givenValue, expensiveSupplier);
        assertDefaultConversion(givenValue, expected, actual);
    }

    @Test
    void givenAllNullsWithObjectsWhenNotNullThenThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
          .isThrownBy(() -> requireNonNullElse(null, null));
    }

    private static void assertDefaultConversion(String given, String expected, String actual) {
        if (given == null) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).isEqualTo(given);
        }
    }

}
