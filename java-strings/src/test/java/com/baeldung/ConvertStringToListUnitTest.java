package com.baeldung;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ConvertStringToListUnitTest {

    private final String countries = "Russia,Germany,England,France,Italy";
    private final String ranks = "1,2,3,4,5, 6,7";
    private final String others = ",,,,,";
    private final List<String> expectedCountriesList = Arrays.asList("Russia", "Germany", "England", "France", "Italy");
    private final List<Integer> expectedRanksList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private final List<String> expectedOthersList = Arrays.asList("", "", "", "", "", "");

    @Test
    public void givenString_thenGetListOfStringByJava() {
        List<String> convertedList = stringToStringListInJava(countries);
        assertEquals(expectedCountriesList, convertedList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava() {
        List<String> convertedList = stringToStringListInJava(others);
        assertEquals(expectedOthersList, convertedList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByApache() {
        List<String> convertedList = stringToStringListUsingApacheCommonsLang(others);
        assertEquals(expectedOthersList, convertedList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByGuava() {
        List<String> convertedList = stringToStringListUsingGuava(others);
        assertEquals(expectedOthersList, convertedList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava8() {
        List<String> convertedList = stringToStringListUsingJava8Stream(others);
        assertEquals(expectedOthersList, convertedList);
    }

    @Test
    public void givenString_thenGetListOfStringByApache() {
        List<String> convertedList = stringToStringListUsingApacheCommonsLang(countries);
        assertEquals(expectedCountriesList, convertedList);
    }

    @Test
    public void givenString_thenGetListOfStringByGuava() {
        List<String> convertedList = stringToStringListUsingGuava(countries);
        assertEquals(expectedCountriesList, convertedList);
    }

    @Test
    public void givenString_thenGetListOfStringByJava8() {
        List<String> convertedList = stringToStringListUsingJava8Stream(countries);
        assertEquals(expectedCountriesList, convertedList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByJava() {
        List<Integer> convertedNumberList = stringToIntegerListInJava(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByGuava() {
        List<Integer> convertedNumberList = stringToIntegerListUsingGuava(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByJava8() {
        List<Integer> convertedNumberList = stringToIntegerListUsingJava8Stream(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByApache() {
        List<Integer> convertedNumberList = stringToIntegerListUsingApacheCommonsLang(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }

    public List<Integer> stringToIntegerListUsingJava8Stream(String stringToConvert) {
        List<Integer> listOfInteger = Stream.of(stringToConvert.split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        return listOfInteger;
    }

    public List<Integer> stringToIntegerListUsingGuava(String stringToConvert) {
        List<Integer> listOfInteger = Lists.transform(Splitter.on(",")
            .trimResults()
            .splitToList(stringToConvert), new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.parseInt(input.trim());
                }
            });

        return listOfInteger;
    }

    public List<Integer> stringToIntegerListUsingApacheCommonsLang(String stringToConvert) {
        String[] arrayOfString = StringUtils.split(stringToConvert, ",");
        List<Integer> listOfInteger = new ArrayList<Integer>();
        for (String number : arrayOfString) {
            listOfInteger.add(Integer.parseInt(number.trim()));
        }
        return listOfInteger;
    }

    public List<Integer> stringToIntegerListInJava(String stringToConvert) {
        String[] arrayOfString = stringToConvert.split(",");
        List<Integer> listOfInteger = new ArrayList<Integer>();
        for (String number : arrayOfString) {
            listOfInteger.add(Integer.parseInt(number.trim()));
        }
        return listOfInteger;
    }

    public List<String> stringToStringListUsingJava8Stream(String stringToConvert) {
        List<String> listOfString = Stream.of(stringToConvert.split(",", -1))
            .collect(Collectors.toList());
        return listOfString;
    }

    public List<String> stringToStringListUsingGuava(String stringToConvert) {
        List<String> listOfString = Splitter.on(",")
            .trimResults()
            .splitToList(stringToConvert);
        return listOfString;
    }

    public List<String> stringToStringListUsingApacheCommonsLang(String stringToConvert) {
        List<String> listOfString = Arrays.asList(StringUtils.splitPreserveAllTokens(stringToConvert, ","));
        return listOfString;
    }

    public List<String> stringToStringListInJava(String stringToConvert) {
        List<String> listOfString = Arrays.asList(stringToConvert.split(",", -1));
        return listOfString;
    }

}
