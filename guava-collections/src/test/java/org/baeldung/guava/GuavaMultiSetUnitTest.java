package org.baeldung.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuavaMultiSetUnitTest {

    @Test
    public void givenMultiSet_whenAddAndRemoveValues_shouldReturnCorrectCount() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");
        bookStore.add("Potter");

        assertThat(bookStore.contains("Potter")).isTrue();
        assertThat(bookStore.count("Potter")).isEqualTo(3);

        bookStore.remove("Potter");
        assertThat(bookStore.contains("Potter")).isTrue();
        assertThat(bookStore.count("Potter")).isEqualTo(2);
    }

    @Test
    public void givenMultiSet_whenSetCount_shouldReturnCorrectCount() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.setCount("Potter", 50);
        assertThat(bookStore.count("Potter")).isEqualTo(50);
    }

    @Test
    public void givenMultiSet_whenSettingNegativeCount_shouldThrowException() {
        Multiset<String> bookStore = HashMultiset.create();
        assertThatThrownBy(() -> bookStore.setCount("Potter", -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void givenMultiSet_whenSettingCountWithOldCount_shouldReturnCorrectValue() {
        Multiset<String> bookStore = HashMultiset.create();
        assertThat(bookStore.setCount("Potter", 0, 2)).isTrue();
        assertThat(bookStore.setCount("Potter", 50, 5)).isFalse();
    }

    @Test
    public void givenMap_compareMultiSetOperations() {
        Map<String, Integer> bookStore = new HashMap<>();
        bookStore.put("Potter", 3);

        assertThat(bookStore.containsKey("Potter")).isTrue();
        assertThat(bookStore.get("Potter")).isEqualTo(3);

        bookStore.put("Potter", 2);
        assertThat(bookStore.get("Potter")).isEqualTo(2);

        bookStore.put("Potter", null);
        assertThat(bookStore.containsKey("Potter")).isTrue();

        bookStore.put("Potter", -1);
        assertThat(bookStore.containsKey("Potter")).isTrue();
    }
}
