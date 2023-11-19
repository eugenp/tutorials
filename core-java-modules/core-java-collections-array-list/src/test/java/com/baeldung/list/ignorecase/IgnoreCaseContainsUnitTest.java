package com.baeldung.list.ignorecase;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IgnoreCaseContainsUnitTest {
    private static final List<String> LANGUAGES = Arrays.asList("Java", "Python", "Kotlin", "Ruby", "Javascript", "Go");

    @Test
    void givenStringList_whenCallTheStandardContains_shouldReturnFalse() {
        String searchStr = "jAvA";
        boolean result = LANGUAGES.contains(searchStr);
        assertFalse(result);
    }

    @Test
    void givenStringList_whenSearchIgnoreCaseUsingStreamAPI_shouldReturnTrue() {
        String searchStr = "koTliN";
        boolean result = LANGUAGES.stream().anyMatch(searchStr::equalsIgnoreCase);
        assertTrue(result);
    }

    @Test
    void givenStringList_whenUsingUtilClass_shouldReturnTrue() {
        String searchStr = "ruBY";
        boolean result = IgnoreCaseSearchUtil.ignoreCaseContains(LANGUAGES, searchStr);
        assertTrue(result);
    }

    @Test
    void givenStringList_whenUsingIgnoreCaseStringList_shouldReturnTrue() {
        String searchStr = "pYtHoN";
        List<String> ignoreCaseList = new IgnoreCaseStringList(LANGUAGES);
        boolean result = ignoreCaseList.contains(searchStr);
        assertTrue(result);

        boolean resultContainAll = ignoreCaseList.containsAll(Arrays.asList("pYtHon", "jAvA", "koTliN", "ruBY"));
        assertTrue(resultContainAll);
    }
}
