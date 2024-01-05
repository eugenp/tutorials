package com.baeldung.string.rmcharsinanotherstring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RemoveCharsInAnotherStringUnitTest {

    private static final String STRING = "a b c d e f g h i j";
    private static final String OTHER = "bdfhj";

    String nestedLoopApproach(String theString, String other) {
        StringBuilder sb = new StringBuilder();
        for (char c : theString.toCharArray()) {
            boolean found = false;
            for (char o : other.toCharArray()) {
                if (c == o) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    String loopAndIndexOfApproach(String theString, String other) {
        StringBuilder sb = new StringBuilder();
        for (char c : theString.toCharArray()) {
            if (other.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    String hashSetApproach(String theString, String other) {
        StringBuilder sb = new StringBuilder();
        Set<Character> set = new HashSet<>(other.length());
        for (char c : other.toCharArray()) {
            set.add(c);
        }

        for (char i : theString.toCharArray()) {
            if (!set.contains(i)) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    @Test
    void whenUsingTheNestedLoopApproach_thenGetExpectedResult() {
        String result = nestedLoopApproach(STRING, OTHER);
        assertEquals("a  c  e  g  i ", result);
    }

    @Test
    void whenUsingTheLoopAndIndexOfApproach_thenGetExpectedResult() {
        String result = loopAndIndexOfApproach(STRING, OTHER);
        assertEquals("a  c  e  g  i ", result);
    }

    @Test
    void whenUsingTheHashSetApproach_thenGetExpectedResult() {
        String result = hashSetApproach(STRING, OTHER);
        assertEquals("a  c  e  g  i ", result);
    }

}