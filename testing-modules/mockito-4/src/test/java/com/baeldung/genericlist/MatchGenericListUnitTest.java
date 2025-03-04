package com.baeldung.genericlist;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

interface MyInterface {
    default String extractFirstLetters(List<String> words) {
        return String.join(", ", words.stream().map(str -> str.substring(0, 1)).toList());
    }
}

public class MatchGenericListUnitTest {
    @Test
    void whenUseAnyWithListClass_thenCorrect() {
        MyInterface mock = Mockito.mock(MyInterface.class);
        when(mock.extractFirstLetters(any(List.class))).thenReturn("a, b, c, d, e");
        assertEquals("a, b, c, d, e", mock.extractFirstLetters(new ArrayList<String>()));
    }

    @Test
    void whenUseAnyListWithTypeParameterInJava7_thenCorrect() {
        //Java 7
        MyInterface mock = Mockito.mock(MyInterface.class);
        when(mock.extractFirstLetters(ArgumentMatchers.<String>anyList())).thenReturn("a, b, c, d, e");
        assertEquals("a, b, c, d, e", mock.extractFirstLetters(new ArrayList<>()));
    }

    @Test
    void whenUseAnyList_thenCorrect() {
        MyInterface mock = Mockito.mock(MyInterface.class);
        when(mock.extractFirstLetters(anyList())).thenReturn("a, b, c, d, e");
        assertEquals("a, b, c, d, e", mock.extractFirstLetters(new ArrayList<>()));
    }

    @Test
    void whenUseAny_thenCorrect() {
        MyInterface mock = Mockito.mock(MyInterface.class);
        when(mock.extractFirstLetters(any())).thenReturn("a, b, c, d, e");
        assertEquals("a, b, c, d, e", mock.extractFirstLetters(new ArrayList<>()));
    }

}