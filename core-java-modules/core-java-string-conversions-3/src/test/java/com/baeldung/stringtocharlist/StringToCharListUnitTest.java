package com.baeldung.stringtocharlist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToCharListUnitTest {
    public String inputString = "Convert a String to a List of Characters in Java";

    @Test
    public void givenString_whenUsingToCharArray_thenConvertToCharList() {
        char[] charArray = inputString.toCharArray();

        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }

        assertEquals(inputString.length(), charList.size());
    }

    @Test
    public void givenString_whenUsingMapToObj_thenConvertToCharList() {
        List<Character> charList = inputString.chars()
                .mapToObj(c -> (char) c)
                .toList();

        assertEquals(inputString.length(), charList.size());
    }

    @Test
    public void givenString_whenUsingSplit_thenConvertToStringList() {
        String[] charArray = inputString.split("");

        List<String> charList = Arrays.asList(charArray);

        assertEquals(inputString.length(), charList.size());
    }

    @Test
    public void givenString_whenUsingGuavaLists_thenConvertToCharList() {
        List<Character> charList = Lists.charactersOf(inputString);

        assertEquals(inputString.length(), charList.size());
    }

    @Test
    public void givenString_whenUsingCodePoints_thenConvertToCharList() {
        List<Character> charList = inputString.codePoints()
                .mapToObj(c -> (char) c)
                .toList();

        assertEquals(inputString.length(), charList.size());
    }
}
