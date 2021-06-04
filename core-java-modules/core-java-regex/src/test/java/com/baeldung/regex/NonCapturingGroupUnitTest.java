package com.baeldung.regex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NonCapturingGroupUnitTest {

  private static final Pattern SIMPLE_URL_PATTERN = Pattern.compile("[^:]+://(?:[.a-z]+/?)+");
  private static final Pattern SCOPED_CASE_INSENSITIVE_URL_PATTERN = Pattern.compile("[^:]+://(?i:[.a-z]+/?)+");
  private static final Pattern SIMPLE_URL_PATTERN_WITH_SPECIFIC_ENDING_PATH = Pattern.compile("[^:]+://(?:[.a-z]+/?)+?/ending-path");
  private static final Pattern SCOPED_CASE_INSENSTIIVE_URL_PATTERN_WITH_ENDING_PATH = Pattern.compile("[^:]://(?i:[.a-z]+/?)+?/ending-path");
  private static final Pattern CASE_INSENSITIVE_URL_PATTERN = Pattern.compile("[^:]+://(?:[.a-z]+/?)+?(/ending-path)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SCOPED_CASE_SENSITIVE_URL_PATTERN = Pattern.compile("[^:]+://(?-i:[.a-z]+/?)+?(/ending-path)", Pattern.CASE_INSENSITIVE);
  private static final Pattern INDEPENDENT_URL_PATTERN_WITH_ENDING_PATH = Pattern.compile("[^:]+://(?>[.a-z]+/?)+/ending-path");
  private static final Pattern INDEPENDENT_URL_PATTERN_WITH_ENDING_PATH_AND_BACKTRACKING = Pattern.compile("[^:]+://(?>(?:[.a-z]+/?)+/)ending-path");

  @Test
  void givenSimpleUrlPattern_whenValidUrlProvided_thenMatches() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN.matcher("http://www.microsoft.com/");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
    Assertions.assertThatThrownBy(() -> urlMatcher.group(1)).isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  void whenSimpleUrlProvidedWithPathProvided_thenMatches() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN.matcher("http://www.microsoft.com/live");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void whenSimpleUrlProvidedWithPathEndingWithSlashProvided_thenMatches() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN.matcher("http://www.microsoft.com/live/");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void givenSimpleUrlPattern_whenUrlWithMultiplePathSegmentsProvided_thenMatches() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN.matcher("http://www.microsoft.com/some/other/url/path");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void whenUrlWithUppercaseCharactersProvided_thenDoesNotMatch() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN.matcher("http://www.Microsoft.com/");
    Assertions.assertThat(urlMatcher.matches()).isFalse();
  }

  @Test
  void givenPatternWithCaseInsensitiveGroup_whenUrlHasUppercaseCharactersInsideOfScope_thenMatches() {
    Matcher urlMatcher = SCOPED_CASE_INSENSITIVE_URL_PATTERN.matcher("http://www.Microsoft.com/");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void givenCaseInsensitivePattern_whenUrlHasUppercaseCharacters_thenMatches() {
    Matcher urlMatcher = CASE_INSENSITIVE_URL_PATTERN.matcher("http://www.Microsoft.com/Ending-path");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
    Assertions.assertThat(urlMatcher.group(1)).isEqualTo("/Ending-path");
  }

  @Test
  void givenPatternWithCaseInsensitiveGroup_whenUrlHasUppercaseCharactersOutsideOfScope_thenMatchFails() {
    Matcher urlMatcher = SCOPED_CASE_INSENSTIIVE_URL_PATTERN_WITH_ENDING_PATH.matcher("http://www.Microsoft.com/Ending-path");
    Assertions.assertThat(urlMatcher.matches()).isFalse();
  }

  @Test
  void givenPatternAllowingBacktracking_whenUrlWithEndingPathCausingBacktrackingProvided_thenMatches() {
    Matcher urlMatcher = SIMPLE_URL_PATTERN_WITH_SPECIFIC_ENDING_PATH.matcher("http://www.microsoft.com/ending-path");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void givenPatternWithIndependentNonCapturingGroup_whenBacktrackingOccurs_thenDoesNotMatch() {
    Matcher independentMatcher = INDEPENDENT_URL_PATTERN_WITH_ENDING_PATH.matcher("http://www.microsoft.com/ending-path");
    Assertions.assertThat(independentMatcher.matches()).isFalse();
  }

  @Test
  void givenPatternWithIndependentNonCapturingGroup_whenBacktrackingDoesNotOccur_thenMatches() {
    Matcher independentMatcher = INDEPENDENT_URL_PATTERN_WITH_ENDING_PATH.matcher("http://www.microsoft.com//ending-path");
    Assertions.assertThat(independentMatcher.matches()).isTrue();
  }

  @Test
  void givenPatternWithIndependentNonCapturingGroup_whenBacktrackingOccursInsideGroup_thenMatches() {
    Matcher independentMatcher = INDEPENDENT_URL_PATTERN_WITH_ENDING_PATH_AND_BACKTRACKING.matcher("http://www.microsoft.com/ending-path");
    Assertions.assertThat(independentMatcher.matches()).isTrue();
  }

  @Test
  void givenCaseInsensitivePatternWithCaseSensitivieSubPattern_whenUrlWithUppercaseCharactersOutsideOfScopeProvided_thenMatches() {
    Matcher urlMatcher = SCOPED_CASE_SENSITIVE_URL_PATTERN.matcher("http://www.microsoft.com/ENDING-PATH");
    Assertions.assertThat(urlMatcher.matches()).isTrue();
  }

  @Test
  void givenCaseInsensitivePatternWithCaseSensitivieSubPattern_whenUrlWithUppercaseCharactersInsideOfScopeProvided_thenDoesNotMatch() {
    Matcher urlMatcher = SCOPED_CASE_SENSITIVE_URL_PATTERN.matcher("http://www.Microsoft.com/ending-path");
    Assertions.assertThat(urlMatcher.matches()).isFalse();
  }
}
