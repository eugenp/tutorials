package com.baeldung.stringtocamelcase;


import com.google.common.base.CaseFormat;
import org.apache.commons.text.CaseUtils;
import org.junit.Test;

import static com.baeldung.stringtocamelcase.StringToCamelCase.*;
import static org.assertj.core.api.Assertions.*;


public class StringToCamelCaseUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseByIteration_ThenReturnCamelCase() {
        assertThat(toCamelCaseByIteration("THIS STRING SHOULD BE IN CAMEL CASE", ' ')).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseByIteration_ThenReturnCamelCase() {
        assertThat(toCamelCaseByIteration("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", '_')).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseBySplitting_ThenReturnCamelCase() {
        assertThat(toCamelCaseBySplitting("THIS STRING SHOULD BE IN CAMEL CASE", " ")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseBySplitting_ThenReturnCamelCase() {
        assertThat(toCamelCaseBySplitting("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", "_")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseBySplittingUsingStreams_ThenReturnCamelCase() {
        assertThat(toCamelCaseBySplittingUsingStreams("THIS STRING SHOULD BE IN CAMEL CASE", " ")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseBySplittingUsingStreams_ThenReturnCamelCase() {
        assertThat(toCamelCaseBySplittingUsingStreams("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", "_")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseUsingApacheCommonsText_ThenReturnCamelCase() {
        assertThat(CaseUtils.toCamelCase("THIS STRING SHOULD BE IN CAMEL CASE", false, ' '))
          .isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseUsingApacheCommonsText_ThenReturnCamelCase() {
        assertThat(CaseUtils.toCamelCase("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", false, '_'))
          .isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseUsingICU4J_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingICU4J("THIS STRING SHOULD BE IN CAMEL CASE", " ")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseUsingICU4J_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingICU4J("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", "_")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseUsingGuava_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingGuava("THIS STRING SHOULD BE IN CAMEL CASE", " ")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseUsingGuava_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingGuava("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", "_")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseUsingApacheCommons_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingApacheCommons("THIS STRING SHOULD BE IN CAMEL CASE", ' ')).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithUnderscores_WhenToCamelCaseUsingApacheCommons_ThenReturnCamelCase() {
        assertThat(toCamelCaseUsingApacheCommons("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE", '_')).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenToCamelCaseByRegex_ThenReturnCamelCase() {
        assertThat(toCamelCaseByRegex("THIS STRING SHOULD BE IN CAMEL CASE")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenStringWithWhiteUnderscores_WhenToCamelCaseByRegex_ThenReturnCamelCase() {
        assertThat(toCamelCaseByRegex("THIS_STRING_SHOULD_BE_IN_CAMEL_CASE")).isEqualTo("thisStringShouldBeInCamelCase");
    }

    @Test
    public void givenRandomString_WhenToCamelCaseByRegex_ThenReturnCamelCase() {
        assertThat(toCamelCaseByRegex("Please Turn this56738 to camel Case")).isEqualTo("pleaseTurnThis56738ToCamelCase");
    }

    @Test
    public void givenRandomStringWithDifferentDelimiters_WhenToCamelCaseByRegex_ThenReturnCamelCase() {
        assertThat(toCamelCaseByRegex("Please Turn this56738 to camel Case This should-be_in;camel-case")).isEqualTo("pleaseTurnThis56738ToCamelCaseThisShouldBeInCamelCase");
    }

    @Test
    public void givenUppercaseWordWithUnderscores_WhenCaseFormatToLowerCamel_ThenReturnCamelCase() {
        assertThat(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "THIS_STRING_SHOULD_BE_IN_CAMEL_CASE")).isEqualTo("thisStringShouldBeInCamelCase");
    }

}
