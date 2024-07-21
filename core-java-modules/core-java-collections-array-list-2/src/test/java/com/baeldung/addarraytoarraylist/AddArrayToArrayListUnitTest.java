package com.baeldung.addarraytoarraylist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class AddArrayToArrayListUnitTest {

    private final static String[] ARRAY1 = { "Java", "Kotlin", "Sql", "Javascript" };
    private final static String[] ARRAY2 = { "C", "C++", "C#", "Typescript" };
    private final static String[] ARRAY3 = { "Python", "Ruby", "Go", "Rust" };

    //@formatter:off
    private final static List<String> EXPECTED = List.of(
        "Languages", ":",
        "Java", "Kotlin", "Sql", "Javascript",
        "C", "C++", "C#", "Typescript",
        "Python", "Ruby", "Go", "Rust");
    //@formatter:on

    private List<String> initLanguageList() {
        List<String> languageList = new ArrayList<>();
        languageList.add("Languages");
        languageList.add(":");
        return languageList;
    }

    @Test
    void whenConvertArrayToListThenAddAll_thenCorrect() {
        List<String> languageList = initLanguageList();

        for (String[] array : List.of(ARRAY1, ARRAY2, ARRAY3)) {
            languageList.addAll(Arrays.asList(array));
        }
        assertEquals(EXPECTED, languageList);
    }

    @Test
    void whenUsingCollectionsAddAll_thenCorrect() {
        List<String> languageList = initLanguageList();

        for (String[] array : List.of(ARRAY1, ARRAY2, ARRAY3)) {
            Collections.addAll(languageList, array);
        }
        assertEquals(EXPECTED, languageList);
    }

    @Test
    void whenUsingStream_thenCorrect() {
        List<String> languageList = initLanguageList();

        Stream.of(ARRAY1, ARRAY2, ARRAY3)
            .flatMap(Arrays::stream)
            .forEachOrdered(languageList::add);
        assertEquals(EXPECTED, languageList);
    }
}