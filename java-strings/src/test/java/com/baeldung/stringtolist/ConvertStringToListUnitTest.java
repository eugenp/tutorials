package com.baeldung.stringtolist;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;



public class ConvertStringToListUnitTest {

    private final ConvertStringToList stringToListConverter = new ConvertStringToList();
    private final String countries = "Russia,Germany,England,France,Italy";
    private final String ranks = "1,2,3,4,5, 6,7";
    private final String others = ",,,,,";
    private final List<String> expectedCountriesList = Arrays.asList("Russia","Germany","England","France","Italy");
    private final List<Integer> expectedRanksList = Arrays.asList(1,2,3,4,5,6,7);
    private final List<String> expectedOthersList = Arrays.asList("","","","","","");
    
    @Test
    public void givenString_thenGetListOfStringByJava() {
        List<String> convertedList = stringToListConverter.stringToStringListInJava(countries);
        assertEquals(expectedCountriesList, convertedList);
    }
 
    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava() {
        List<String> convertedList =stringToListConverter.stringToStringListInJava(others);
        assertEquals(expectedOthersList, convertedList);
    }
    
    @Test
    public void givenEmptyStrings_thenGetListOfStringByApache() {
        List<String> convertedList =stringToListConverter.stringToStringListUsingApacheCommonsLang(others);
        assertEquals(expectedOthersList, convertedList);
    }
    
    @Test
    public void givenEmptyStrings_thenGetListOfStringByGuava() {
        List<String> convertedList =stringToListConverter.stringToStringListUsingGuava(others);
        assertEquals(expectedOthersList, convertedList);
    }
    
    @Test
    public void givenEmptyStrings_thenGetListOfStringByJava8() {
        List<String> convertedList =stringToListConverter.stringToStringListUsingJava8Stream(others);
        assertEquals(expectedOthersList, convertedList);
    }
    
    @Test
    public void givenString_thenGetListOfStringByApache() {
        List<String> convertedList = stringToListConverter.stringToStringListUsingApacheCommonsLang(countries);
        assertEquals(expectedCountriesList, convertedList);
    }
    
    @Test
    public void givenString_thenGetListOfStringByGuava() {
        List<String> convertedList = stringToListConverter.stringToStringListUsingGuava(countries);
        assertEquals(expectedCountriesList, convertedList);
    }
    
    @Test
    public void givenString_thenGetListOfStringByJava8() {
        List<String> convertedList =stringToListConverter.stringToStringListUsingJava8Stream(countries);
        assertEquals(expectedCountriesList, convertedList);
    }
    
    @Test
    public void givenString_thenGetListOfIntegerByJava() {
        List<Integer> convertedNumberList = stringToListConverter.stringToIntegerListInJava(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }
    
    @Test
    public void givenString_thenGetListOfIntegerByGuava() {
        List<Integer> convertedNumberList = stringToListConverter.stringToIntegerListUsingGuava(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }
    
    @Test
    public void givenString_thenGetListOfIntegerByJava8() {
        List<Integer> convertedNumberList = stringToListConverter.stringToIntegerListUsingJava8Stream(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }
    
    @Test
    public void givenString_thenGetListOfIntegerByApache() {
        List<Integer> convertedNumberList = stringToListConverter.stringToIntegerListUsingApacheCommonsLang(ranks);
        assertEquals(expectedRanksList, convertedNumberList);
    }
    
}
