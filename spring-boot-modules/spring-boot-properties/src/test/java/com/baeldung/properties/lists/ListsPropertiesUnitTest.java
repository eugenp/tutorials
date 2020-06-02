package com.baeldung.properties.lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringListPropertiesApplication.class})
public class ListsPropertiesUnitTest {

    @Value("${arrayOfStrings}")
    private String[] arrayOfStrings;

    @Value("${arrayOfStrings}")
    private List<String> unexpectedListOfStrings;

    @Value("#{'${arrayOfStrings}'.split(',')}")
    private List<String> listOfStrings;

    @Value("#{${listOfStrings}}")
    private List<String> listOfStringsV2;

    @Value("#{'${listOfStringsWithCustomDelimiter}'.split(';')}")
    private List<String> listOfStringsWithCustomDelimiter;

    @Value("#{'${listOfBooleans}'.split(',')}")
    private List<Boolean> listOfBooleans;

    @Value("#{'${listOfIntegers}'.split(',')}")
    private List<Integer> listOfIntegers;

    @Value("#{'${listOfCharacters}'.split(',')}")
    private List<Character> listOfCharacters;

    @Autowired
    private Environment environment;

    @Test
    public void whenContextIsInitialized_ThenInjectedArrayContainsExpectedValues() {
        assertEquals(arrayOfStrings, new String[] {"Baeldung", "dot", "com"});
    }

    @Test
    public void whenContextIsInitialized_ThenInjectedListContainsUnexpectedValues() {
        assertEquals(unexpectedListOfStrings, Collections.singletonList("Baeldung,dot,com"));
    }

    @Test
    public void whenContextIsInitialized_ThenInjectedListContainsExpectedValues() {
        assertEquals(listOfStrings, Arrays.asList("Baeldung", "dot", "com"));
    }

    @Test
    public void whenContextIsInitialized_ThenInjectedListV2ContainsExpectedValues() {
        assertEquals(listOfStringsV2, Arrays.asList("Baeldung", "dot", "com"));
    }

    @Test
    public void whenContextIsInitialized_ThenInjectedListWithCustomDelimiterContainsExpectedValues() {
        assertEquals(listOfStringsWithCustomDelimiter, Arrays.asList("Baeldung", "dot", "com"));
    }

    @Test
    public void whenContextIsInitialized_ThenInjectedListOfBasicTypesContainsExpectedValues() {
        assertEquals(listOfBooleans, Arrays.asList(false, false, true));
        assertEquals(listOfIntegers, Arrays.asList(1, 2, 3, 4));
        assertEquals(listOfCharacters, Arrays.asList('a', 'b', 'c'));
    }

    @Test
    public void WhenReadingFromSpringEnvironment_ThenPropertiesHaveExpectedValues() {
        String[] arrayOfStrings = environment.getProperty("arrayOfStrings", String[].class);
        List<String> listOfStrings = (List<String>)environment.getProperty("arrayOfStrings", List.class);

        assertEquals(arrayOfStrings, new String[] {"Baeldung", "dot", "com"});
        assertEquals(listOfStrings, Arrays.asList("Baeldung", "dot", "com"));
    }
}
