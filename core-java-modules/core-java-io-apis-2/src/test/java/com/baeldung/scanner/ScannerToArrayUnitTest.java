package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;

public class ScannerToArrayUnitTest {

    @Test
    void whenMultipleElementsInOneLine_thenGetExpectedArray() {
        String input = "Java Kotlin Ruby Python Go\n";
        String[] expected = new String[] { "Java", "Kotlin", "Ruby", "Python", "Go" };

        // scanner.next()
        Scanner scanner1 = new Scanner(input);
        String[] result1 = new String[5];
        int i = 0;
        while (i < result1.length) {
            result1[i] = scanner1.next();
            i++;
        }
        assertArrayEquals(expected, result1);

        //split()
        Scanner scanner2 = new Scanner(input);
        String[] result2 = scanner2.nextLine()
          .split("\\s+");
        assertArrayEquals(expected, result2);
    }

    @Test
    void whenOneElementPerLine_thenGetExpectedArray() {
        String input = new StringBuilder().append("Baeldung Java\n")
          .append("Baeldung Kotlin\n")
          .append("Baeldung Linux\n")
          .toString();

        String[] expected = new String[] { "Baeldung Java", "Baeldung Kotlin", "Baeldung Linux" };

        String[] result = new String[3];
        Scanner scanner = new Scanner(input);
        int i = 0;
        while (i < result.length) {
            result[i] = scanner.nextLine();
            i++;
        }
        assertArrayEquals(expected, result);
    }

    @Test
    void whenOneElementPerLine_thenGetExpectedList() {
        String input = new StringBuilder().append("Baeldung Java\n")
          .append("Baeldung Kotlin\n")
          .append("Baeldung Linux\n")
          .toString();

        List<String> expected = Lists.newArrayList("Baeldung Java", "Baeldung Kotlin", "Baeldung Linux");

        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        assertEquals(expected, result);
    }

    @Test
    void whenEveryTokenIsAnElement_thenGetExpectedList() {
        String input = new StringBuilder().append("Linux Windows MacOS\n")
          .append("Java Kotlin Python Go\n")
          .toString();

        List<String> expected = Lists.newArrayList("Linux", "Windows", "MacOS", "Java", "Kotlin", "Python", "Go");
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(input);
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }
        assertEquals(expected, result);
    }

    @Test
    void whenEveryTokenIsAnElement_thenGetExpectedArray() {
        String input = new StringBuilder().append("Linux Windows MacOS\n")
          .append("Java Kotlin Python Go\n")
          .toString();

        String[] expected = new String[] { "Linux", "Windows", "MacOS", "Java", "Kotlin", "Python", "Go" };
        String[] result = new String[] {};

        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String[] lineInArray = scanner.nextLine()
              .split("\\s+");
            result = ObjectArrays.concat(result, lineInArray, String.class);
        }
        assertArrayEquals(expected, result);
    }
}