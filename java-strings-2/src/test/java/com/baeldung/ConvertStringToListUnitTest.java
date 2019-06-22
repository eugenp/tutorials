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
    private final String emptyStrings = ",,,,,";
    private final List<String> expectedCountriesList = Arrays.asList("Russia", "Germany", "England", "France", "Italy");
    private final List<Integer> expectedRanksList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private final List<String> expectedEmptyStringsList = Arrays.asList("", "", "", "", "", "");

    @Test
    public void givenString_thenGetListOfStringByJava() {
        List<String> convertedCountriesList = Arrays.asList(countries.split(",", -1));

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void givenString_thenGetListOfStringByApache() {
        List<String> convertedCountriesList = Arrays.asList(StringUtils.splitPreserveAllTokens(countries, ","));

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void givenString_thenGetListOfStringByGuava() {
        List<String> convertedCountriesList = Splitter.on(",")
            .trimResults()
            .splitToList(countries);

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void givenString_thenGetListOfStringByJava8() {
        List<String> convertedCountriesList = Stream.of(countries.split(",", -1))
            .collect(Collectors.toList());

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByJava() {
        String[] convertedRankArray = ranks.split(",");
        List<Integer> convertedRankList = new ArrayList<Integer>();
        for (String number : convertedRankArray) {
            convertedRankList.add(Integer.parseInt(number.trim()));
        }

        assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByGuava() {
        List<Integer> convertedRankList = Lists.transform(Splitter.on(",")
            .trimResults()
            .splitToList(ranks), new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.parseInt(input.trim());
                }
            });

        assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByJava8() {
        List<Integer> convertedRankList = Stream.of(ranks.split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void givenString_thenGetListOfIntegerByApache() {
        String[] convertedRankArray = StringUtils.split(ranks, ",");
        List<Integer> convertedRankList = new ArrayList<Integer>();
        for (String number : convertedRankArray) {
            convertedRankList.add(Integer.parseInt(number.trim()));
        }

        assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava() {
        List<String> convertedEmptyStringsList = Arrays.asList(emptyStrings.split(",", -1));

        assertEquals(expectedEmptyStringsList, convertedEmptyStringsList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByApache() {
        List<String> convertedEmptyStringsList = Arrays.asList(StringUtils.splitPreserveAllTokens(emptyStrings, ","));

        assertEquals(expectedEmptyStringsList, convertedEmptyStringsList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByGuava() {
        List<String> convertedEmptyStringsList = Splitter.on(",")
            .trimResults()
            .splitToList(emptyStrings);

        assertEquals(expectedEmptyStringsList, convertedEmptyStringsList);
    }

    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava8() {
        List<String> convertedEmptyStringsList = Stream.of(emptyStrings.split(",", -1))
            .collect(Collectors.toList());

        assertEquals(expectedEmptyStringsList, convertedEmptyStringsList);
    }

}
