package com.baeldung.removeleadingtrailingchar;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoveLeadingAndTrailingZeroesUnitTest {

    public static Stream<Arguments> leadingZeroTestProvider() {
        return Stream.of(Arguments.of("", ""), Arguments.of("abc", "abc"), Arguments.of("123", "123"), Arguments.of("0abc", "abc"), Arguments.of("0123", "123"), Arguments.of("0000123", "123"), Arguments.of("1230", "1230"), Arguments.of("01230", "1230"), Arguments.of("01", "1"),
            Arguments.of("0001", "1"), Arguments.of("0", "0"), Arguments.of("00", "0"), Arguments.of("0000", "0"), Arguments.of("12034", "12034"), Arguments.of("1200034", "1200034"), Arguments.of("0012034", "12034"), Arguments.of("1203400", "1203400"));
    }

    public static Stream<Arguments> trailingZeroTestProvider() {
        return Stream.of(Arguments.of("", ""), Arguments.of("abc", "abc"), Arguments.of("123", "123"), Arguments.of("abc0", "abc"), Arguments.of("1230", "123"), Arguments.of("1230000", "123"), Arguments.of("0123", "0123"), Arguments.of("01230", "0123"), Arguments.of("10", "1"),
            Arguments.of("1000", "1"), Arguments.of("0", "0"), Arguments.of("00", "0"), Arguments.of("0000", "0"), Arguments.of("12034", "12034"), Arguments.of("1200034", "1200034"), Arguments.of("0012034", "0012034"), Arguments.of("1203400", "12034"));
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithStringBuilder_thenReturnWithoutLeadingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithStringBuilder(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithStringBuilder_thenReturnWithoutTrailingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithStringBuilder(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithSubstring_thenReturnWithoutLeadingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithSubstring(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithSubstring_thenReturnWithoutTrailingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithSubstring(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithApacheCommonsStripStart_thenReturnWithoutLeadingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithApacheCommonsStripStart(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithApacheCommonsStripEnd_thenReturnWithoutTrailingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithApacheCommonsStripEnd(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithGuavaTrimLeadingFrom_thenReturnWithoutLeadingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithGuavaTrimLeadingFrom(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithGuavaTrimTrailingFrom_thenReturnWithoutTrailingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithGuavaTrimTrailingFrom(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithRegex_thenReturnWithoutLeadingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithRegex(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithRegex_thenReturnWithoutTrailingZeroes(String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithRegex(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

}
