package org.baeldung.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaMultiSetUnitTest {

        @Test
        public void add_multipleValues() {
                Multiset<String> bookStore = HashMultiset.create();
                bookStore.add("Potter");
                bookStore.add("Potter");

                assertThat(bookStore.contains("Potter")).isTrue();
                assertThat(bookStore.count("Potter")).isEqualTo(2);
        }
}
