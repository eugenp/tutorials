package com.baeldung.java9.entries;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class SimpleCustomKeyValueUnitTest {


    @Test
    public void givenModifiableEntries_whenVerifying_thenShouldContainKeyValues() {
        SimpleCustomKeyValue<String, String> firstEntry = new SimpleCustomKeyValue<>();
        firstEntry.setKey("key1");
        firstEntry.setValue("value1");

        SimpleCustomKeyValue<String, String> secondEntry = new SimpleCustomKeyValue<>("key2", "value2");
        secondEntry.setKey("different key");

        assertThat(Stream.of(firstEntry, secondEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key1", "value1"),
            tuple("different key", "value2"));
    }

}