package com.baeldung.regex.findnumbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;

/**
 * Unit Test to find Integers and Decimal Numbers in a String
 */
public class FindNumbersUnitTest {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");
    private static final Pattern DECIMAL_NUM_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    
    private static final String STR_WITH_ALL_DIGITS = "970987678607608";
    
    private static final String STR_WITH_INTEGERS_SEP_BY_PERIODS
      = "3453..5.-23532...32432.-2363.3454......345.-34.";
    private static final String STR_WITH_INTEGERS_SEP_BY_NON_DIGITS_INCL_PERIODS
      = "646lkl~4-53l-k34.fdsf.-ds-35.45f9wg03868*()(k;-95786fsd79-86";
    
    private static final String STR_WITH_DECIMAL_NUMS_SEP_BY_NON_DIGITS_INCL_PERIODS
      = ".7854.455wo.rdy(do.g)-3.-553.00.53;good^night%o3456sdcardR%3567.4%£cat";
    private static final String STR_WITH_RANDOM_DIGITS_DASHES_AND_PERIODS
      = ".-..90834.345.--0493-..-85.-875.345-.-.-355.345...345.-.636-5.6-3.";
    
    private static long[] findIntegersUsingRegexPatternOnString(String stringToSearch) {
        Matcher matcher = INTEGER_PATTERN.matcher(stringToSearch);

        List<Long> integerList = new ArrayList<>();
        while (matcher.find()) {
            Long integerItem = Long.parseLong(matcher.group());
            integerList.add(integerItem);
        }

        return Longs.toArray(integerList);
    }

    private static double[] findDecimalNumsUsingRegexPatternOnString(String stringToSearch) {
        Matcher matcher = DECIMAL_NUM_PATTERN.matcher(stringToSearch);

        List<Double> decimalNumList = new ArrayList<>();
        while (matcher.find()) {
            Double decimalNumItem = Double.parseDouble(matcher.group());
            decimalNumList.add(decimalNumItem);
        }

        return Doubles.toArray(decimalNumList);
    }

    @Test
    public void givenStringOfAllDigits_whenRegexMatchByInteger_thenWholeStrMatchedAsOneInteger() {     
        long[] integersFound = findIntegersUsingRegexPatternOnString(STR_WITH_ALL_DIGITS);

        assertEquals(1, integersFound.length);
        assertEquals(970987678607608L, integersFound[0]);
    }

    @Test
    public void givenStringWithIntegersSepByPeriods_whenRegexMatchByInteger_thenExpectedIntegersFound() {
        long[] integersFound = findIntegersUsingRegexPatternOnString(STR_WITH_INTEGERS_SEP_BY_PERIODS);

        assertTrue(Arrays.equals(new long[] {3453L,5L,-23532L,32432L,-2363L,3454L,345L,-34L}, integersFound));
    }

    @Test
    public void givenStringWithIntegersSepByNonDigitsInclPeriods_whenRegexMatchByInteger_thenExpectedIntegersFound() {
        long[] integersFound = findIntegersUsingRegexPatternOnString(STR_WITH_INTEGERS_SEP_BY_NON_DIGITS_INCL_PERIODS);

        assertTrue(Arrays.equals(new long[] {646L,4L,-53L,34L,-35L,45L,9L,3868L,-95786L,79L,-86L}, integersFound));
    }
    
    @Test
    public void givenStringOfAllDigits_whenRegexMatchByDecimalNumber_thenWholeStrMatchedAsOneDecimalNumber() {
        double[] decimalNumsFound = findDecimalNumsUsingRegexPatternOnString(STR_WITH_ALL_DIGITS);
        
        assertEquals(1, decimalNumsFound.length);
        assertEquals(970987678607608D, decimalNumsFound[0]);
    }
    
    @Test
    public void givenStringOfDecimalNumsSepByNonDigitsInclPeriods_whenRegexMatchByDecimalNumber_thenExpectedNumsFound() {
        double[] decimalNumsFound = findDecimalNumsUsingRegexPatternOnString(STR_WITH_DECIMAL_NUMS_SEP_BY_NON_DIGITS_INCL_PERIODS);
        
        assertTrue(Arrays.equals(new double[] {7854.455, -3, -553.00, 53, 3456, 3567.4}, decimalNumsFound));
    }
    
    @Test
    public void givenStringWithRandomDigitsDashesAndPeriods_whenRegexMatchByDecimalNumber_thenExpectedNumsFound() {
        double[] decimalNumsFound = findDecimalNumsUsingRegexPatternOnString(STR_WITH_RANDOM_DIGITS_DASHES_AND_PERIODS);
        
        assertTrue(Arrays.equals(new double[] {90834.345, -493, -85, -875.345, -355.345, 345, 636, -5.6, -3}, decimalNumsFound));
    }
}
