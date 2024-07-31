package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class InputWithSpacesUnitTest {
    @Test
    void whenValuesContainSpaces_thenNextBreaksTheValue() {
        String input = new StringBuilder().append("Michael Jackson\n")
          .append("He was the 'King of Pop'.\n")
          .toString();

        Scanner sc = new Scanner(input);
        String name = sc.next();
        String description = sc.next();
        assertEquals("Michael", name);
        assertEquals("Jackson", description);
    }

    @Test
    void whenOneValuePerLineUsingNextLine_thenGetExpectedResult() {
        String input = new StringBuilder().append("Michael Jackson\n")
          .append("He was the 'King of Pop'.\n")
          .toString();

        Scanner sc = new Scanner(input);
        String name = sc.nextLine();
        String description = sc.nextLine();
        assertEquals("Michael Jackson", name);
        assertEquals("He was the 'King of Pop'.", description);
    }

    @Test
    void whenOneValuePerLineUsingNewLineAsDelimiter_thenGetExpectedResult() {
        String input = new StringBuilder().append("Michael Jackson\n")
          .append("He was the 'King of Pop'.\n")
          .toString();

        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\n");
        String name = sc.next();
        String description = sc.next();
        assertEquals("Michael Jackson", name);
        assertEquals("He was the 'King of Pop'.", description);
    }

    @Test
    void whenValuesAreSeparatedByCommaUsingSplit_thenGetExpectedResult() {
        String input = "Michael Jackson, Whitney Houston, John Lennon\n";

        Scanner sc = new Scanner(input);
        String[] names = sc.nextLine()
          .split(", ");
        assertArrayEquals(new String[] { "Michael Jackson", "Whitney Houston", "John Lennon" }, names);
    }

    @Test
    void whenValuesAreSeparatedByCommaSettingDelimiterWithoutNewline_thenGetExpectedResult() {
        String input = new StringBuilder().append("Michael Jackson, Whitney Houston, John Lennon\n")
          .append("Elvis Presley\n")
          .toString();

        Scanner sc = new Scanner(input);
        sc.useDelimiter(", ");
        List<String> names = new ArrayList<>();
        while (sc.hasNext()) {
            names.add(sc.next());
        }
        //assertEquals(Lists.newArrayList("Michael Jackson", "Whitney Houston", "John Lennon", "Elvis Presley"), names); <-- Fail
        assertEquals(3, names.size());
        assertEquals("John Lennon\nElvis Presley\n", names.get(2));

    }

    @Test
    void whenValuesAreSeparatedByCommaSettingDelimiter_thenGetExpectedResult() {
        String input = new StringBuilder().append("Michael Jackson, Whitney Houston, John Lennon\n")
          .append("Elvis Presley\n")
          .toString();

        Scanner sc = new Scanner(input);
        sc.useDelimiter(", |\\n");
        List<String> names = new ArrayList<>();
        while (sc.hasNext()) {
            names.add(sc.next());
        }
        assertEquals(Lists.newArrayList("Michael Jackson", "Whitney Houston", "John Lennon", "Elvis Presley"), names);
    }
}