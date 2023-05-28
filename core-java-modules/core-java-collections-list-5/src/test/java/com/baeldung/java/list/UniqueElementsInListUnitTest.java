package com.baeldung.java.list;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class UniqueElementsInListUnitTest {
    // @formatter:off
    private static final List<String> MY_LIST = Lists.newArrayList(
      "Microsoft Windows",
      "Mac OS",
      "GNU Linux",
      "Free BSD",
      "GNU Linux",
      "Mac OS");
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