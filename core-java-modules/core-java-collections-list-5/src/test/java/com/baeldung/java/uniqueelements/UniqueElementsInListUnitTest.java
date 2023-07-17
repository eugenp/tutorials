package com.baeldung.java.uniqueelements;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UniqueElementsInListUnitTest {
    // @formatter:off
    private static final List<String> MY_LIST = Arrays.asList(new String[]{
      "Microsoft Windows",
      "Mac OS",
      "GNU Linux",
      "Free BSD",
      "GNU Linux",
      "Mac OS"});
    // @formatter:on

    @Test
    void whenConvertToSet_thenGetExpectedResult() {
        List<String> result = new ArrayList<>(new HashSet<>(MY_LIST));
        assertThat(result).containsExactlyInAnyOrder("Free BSD", "Microsoft Windows", "Mac OS", "GNU Linux");

        result = new ArrayList<>(new LinkedHashSet<>(MY_LIST));
        assertThat(result).containsExactly("Microsoft Windows", "Mac OS", "GNU Linux", "Free BSD");
    }

    @Test
    void whenUsingStream_thenGetExpectedResult() {
        List<String> result = MY_LIST.stream()
          .distinct()
          .collect(toList());
        assertThat(result).containsExactly("Microsoft Windows", "Mac OS", "GNU Linux", "Free BSD");
    }
}