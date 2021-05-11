package com.baeldung.regex.findnumbers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * Unit Test to find Integers and Decimal Numbers in a String
 */
class FindNumbersUnitTest {

    private static List<String> findIntegers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<String> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(matcher.group());
        }

        return integerList;
    }

    private static List<String> findDecimalNums(String stringToSearch) {
        Pattern decimalNumPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = decimalNumPattern.matcher(stringToSearch);

        List<String> decimalNumList = new ArrayList<>();
        while (matcher.find()) {
            decimalNumList.add(matcher.group());
        }

        return decimalNumList;
    }

    @Test
    void givenStrOfAllDigits_whenRegexMatchByInteger_thenWholeStrMatchedAsOneInteger() {     
        List<String> integersFound = findIntegers("970987678607608");

        assertThat(integersFound, contains("970987678607608"));
    }

    @Test
    void givenStrWithIntegersSepByPeriods_whenRegexMatchByInteger_thenExpectedIntegersFound() {
        List<String> integersFound = findIntegers("3453..5.-23532...32432.-2363.3454......345.-34.");

        assertThat(integersFound, contains("3453", "5", "-23532", "32432", "-2363", "3454", "345", "-34"));
    }

    @Test
    void givenStrWithIntegersSepByNonDigitsInclPeriods_whenRegexMatchByInteger_thenExpectedIntegersFound() {
        List<String> integersFound = findIntegers("646lkl~4-53l-k34.fdsf.-ds-35.45f9wg3868*()(k;-95786fsd79-86");

        assertThat(integersFound, contains("646", "4", "-53", "34", "-35", "45", "9", "3868", "-95786", "79", "-86"));
    }
    
    @Test
    void givenStrOfAllDigits_whenRegexMatchByDecimalNumber_thenWholeStrMatchedAsOneDecimalNumber() {
        List<String> decimalNumsFound = findDecimalNums("970987678607608");
        
        assertThat(decimalNumsFound, contains("970987678607608"));
    }
    
    @Test
    void givenStrOfDecimalNumsSepByNonDigitsInclPeriods_whenRegexMatchByDecimalNumber_thenExpectedNumsFound() {
        List<String> decimalNumsFound = findDecimalNums(".7854.455wo.rdy(do.g)-3.-553.00.53;good^night%o3456sdcardR%3567.4%£cat");
        
        assertThat(decimalNumsFound, contains("7854.455", "-3", "-553.00", "53", "3456", "3567.4"));
    }
    
    @Test
    void givenStrWithRandomDigitsDashesAndPeriods_whenRegexMatchByDecimalNumber_thenExpectedNumsFound() {
        List<String> decimalNumsFound = findDecimalNums(".-..90834.345.--493-..-85.-875.345-.-.-355.345...345.-.636-5.6-3.");
        
        assertThat(decimalNumsFound, contains("90834.345", "-493", "-85", "-875.345", "-355.345", "345", "636", "-5.6", "-3"));
    }
}
