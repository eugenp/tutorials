package com.baeldung.java.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListAssertJUnitTest {

    private final List<String> list1 = Arrays.asList("1", "2", "3", "4");
    private final List<String> list2 = Arrays.asList("1", "2", "3", "4");
    private final List<String> list3 = Arrays.asList("1", "2", "4", "3");

    @Test
    public void whenTestingForEquality_ShouldBeEqual() throws Exception {
        assertThat(list1).isEqualTo(list2).isNotEqualTo(list3);

        assertThat(list1.equals(list2)).isTrue();
        assertThat(list1.equals(list3)).isFalse();
    }
}
