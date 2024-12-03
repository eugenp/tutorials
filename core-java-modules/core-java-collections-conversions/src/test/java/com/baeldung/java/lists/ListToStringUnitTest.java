package com.baeldung.java.lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ListToStringUnitTest {

    @Test
    public void whenListToString_thenPrintDefault() {
        List<Integer> intLIst = Arrays.asList(1, 2, 3);
        System.out.println(intLIst);
    }

    @Test
    public void whenStringJoinWithStringList_thenPrintCustom() {
        List<String> strList = Arrays.asList("one", "two", "three");
        System.out.println(String.join(" : ", strList));
    }

    @Test
    public void whenStringJoinWithNonStringList_thenPrintCustom() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<String> strList = intList.stream().map(String::valueOf).collect(Collectors.toList());
        System.out.println(String.join(" : ", strList));
    }

    @Test
    public void whenCollectorsJoining_thenPrintCustom() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        System.out.println(intList.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("-", "{", "}")));
    }

    @Test
    public void whenStringUtilsJoin_thenPrintCustom() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        System.out.println(StringUtils.join(intList, "|"));
    }
}