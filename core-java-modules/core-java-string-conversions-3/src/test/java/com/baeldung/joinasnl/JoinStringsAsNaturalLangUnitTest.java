package com.baeldung.joinasnl;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class JoinStringsAsNaturalLangUnitTest {
    String joinItemsAsNaturalLanguage(List<String> list, boolean oxfordComma) {
        if (list.size() < 3) {
            return String.join(" and ", list);
        }
        // list has at least three elements
        int lastIdx = list.size() - 1;
        StringBuilder sb = new StringBuilder();
        return sb.append(String.join(", ", list.subList(0, lastIdx)))
          .append(oxfordComma ? ", and " : " and ")
          .append(list.get(lastIdx))
          .toString();
    }

    @Test
    void whenCallingJoinByGrammar_thenGetTheExpectedResult() {
        assertEquals("", joinItemsAsNaturalLanguage(emptyList(), false));
        assertEquals("A", joinItemsAsNaturalLanguage(List.of("A"), false));
        assertEquals("A and B", joinItemsAsNaturalLanguage(List.of("A", "B"), false));
        assertEquals("A, B, C, D and I have a comma (,)", joinItemsAsNaturalLanguage(List.of("A", "B", "C", "D", "I have a comma (,)"), false));
        // with oxford comma = true
        assertEquals("", joinItemsAsNaturalLanguage(emptyList(), true));
        assertEquals("A", joinItemsAsNaturalLanguage(List.of("A"), true));
        assertEquals("A and B", joinItemsAsNaturalLanguage(List.of("A", "B"), true));
        assertEquals("A, B, C, D, and I have a comma (,)", joinItemsAsNaturalLanguage(List.of("A", "B", "C", "D", "I have a comma (,)"), true));
    }
}