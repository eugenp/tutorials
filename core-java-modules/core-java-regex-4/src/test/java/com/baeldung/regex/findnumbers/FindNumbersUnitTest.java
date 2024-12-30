package com.baeldung.regex.findnumbers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

/**
 * Unit Test to find Integers, Decimal Numbers, and Scientific Notation and Hexadecimal Numbers in a String
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
    void givenStrOfAllDigits_whenRegexMatchByInt_thenWholeStrMatchedAsOneInt() {     
        List<String> integersFound = findIntegers("970987678607608");

        assertThat(integersFound).containsExactly("970987678607608");
    }

    @Test
    void givenStrWithIntegersSepByPeriods_whenRegexMatchByInt_thenExpectedIntsFound() {
        List<String> integersFound = findIntegers("3453..5.-23532...32432.-2363.3454......345.-34.");

        assertThat(integersFound).containsExactly("3453", "5", "-23532", "32432", "-2363", "3454", "345", "-34");
    }

    @Test
    void givenStrWithIntegersSepByNonDigits_whenRegexMatchByInt_thenExpectedIntsFound() {
        List<String> integersFound = findIntegers("646lkl~4-53l-k34.fdsf.-ds-35.45f9wg3868*()(k;-95786fsd79-86");

        assertThat(integersFound).containsExactly("646", "4", "-53", "34", "-35", "45", "9", "3868", "-95786", "79", "-86");
    }
    
    @Test
    void givenStrOfAllDigits_whenRegexMatchByDecNum_thenWholeStrMatchedAsOneDecimalNumber() {
        List<String> decimalNumsFound = findDecimalNums("970987678607608");
        
        assertThat(decimalNumsFound).containsExactly("970987678607608");
    }
    
    @Test
    void givenStrOfDecNumsSepByNonDigits_whenRegexMatchByDecNum_thenExpectedNumsFound() {
        List<String> decimalNumsFound = findDecimalNums(".7854.455wo.rdy(do.g)-3.-553.00.53;good^night%o3456sdcardR%3567.4%£cat");
        
        assertThat(decimalNumsFound).containsExactly("7854.455", "-3", "-553.00", "53", "3456", "3567.4");
    }
    
    @Test
    void givenStrWithRandomDigitsDashesAndPeriods_whenRegexMatchByDecNum_thenExpectedNumsFound() {
        List<String> decimalNumsFound = findDecimalNums(".-..90834.345.--493-..-85.-875.345-.-.-355.345...345.-.636-5.6-3.");
        
        assertThat(decimalNumsFound).containsExactly("90834.345", "-493", "-85", "-875.345", "-355.345", "345", "636", "-5.6", "-3");
    }
    
    @Test
    void givenStrOfIntsSepByNonDigits_whenRegexMatchByInt_thenExpectedValuesFound() {
        LongStream integerValuesFound = findIntegers(".7854.455wo.rdy(do.g)-3.ght%o34.56")
          .stream().mapToLong(Long::valueOf);
        
        assertThat(integerValuesFound).containsExactly(7854L, 455L, -3L, 34L, 56L);
    }    
    
    @Test
    void givenStrOfDecNumsSepByNonDigits_whenRegexMatchByDecNum_thenExpectedValuesFound() {
        DoubleStream decimalNumValuesFound = findDecimalNums(".7854.455wo.rdy(do.g)-3.ght%o34.56")
          .stream().mapToDouble(Double::valueOf);
        
        assertThat(decimalNumValuesFound).containsExactly(7854.455, -3.0, 34.56);
    }
    
    @Test
    void givenStrOfSciNotationNumsSepByNonDigits_whenRegexMatchBySciNotNum_thenExpectedNumsFound() {
        String strToSearch = "}s1.25E-3>,/@l2e109he-70.96E+105d£d_-8.7312E-102=#;,.d919.3822e+31e]";
        
        Matcher matcher = Pattern.compile("-?\\d+(\\.\\d+)?[eE][+-]?\\d+")
          .matcher(strToSearch);
        List<String> sciNotationNums = new ArrayList<>();
        while (matcher.find()) {
            sciNotationNums.add(matcher.group());
        }
        
        assertThat(sciNotationNums).containsExactly("1.25E-3", "2e109", "-70.96E+105", "-8.7312E-102", "919.3822e+31");
    }
    
    @Test
    void givenStrOfHexNumsSepByNonDigits_whenRegexMatchByHexNum_thenExpectedNumsFound() {
        String strToSearch = "}saF851Bq-3f6Cm>,/@j-2Ad9eE>70ae19.>";
        
        Matcher matcher = Pattern.compile("-?[0-9a-fA-F]+")
          .matcher(strToSearch);
        List<String> hexNums = new ArrayList<>();
        while (matcher.find()) {
            hexNums.add(matcher.group());
        }
        
        assertThat(hexNums).containsExactly("aF851B", "-3f6C", "-2Ad9eE", "70ae19");
    }
}
