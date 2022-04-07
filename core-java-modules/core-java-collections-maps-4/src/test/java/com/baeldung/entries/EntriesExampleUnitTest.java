package com.baeldung.entries;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.apache.commons.collections4.keyvalue.UnmodifiableMapEntry;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class EntriesExampleUnitTest {

    @Test
    public void givenEntries_whenVerifying_thenShouldContainKeyValues() {
        AbstractMap.SimpleEntry<String, String> firstEntry = new AbstractMap.SimpleEntry<>("key1", "value1");
        AbstractMap.SimpleEntry<String, String> secondEntry = new AbstractMap.SimpleEntry<>("key2", "value2");
        AbstractMap.SimpleEntry<String, String> thirdEntry = new AbstractMap.SimpleEntry<>(firstEntry);
        thirdEntry.setValue("a different value");

        assertThat(Stream.of(firstEntry, secondEntry, thirdEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key1", "value1"),
            tuple("key2", "value2"),
            tuple("key1", "a different value"));
    }

    @Test
    public void givenImmutableEntries_whenVerifying_thenShouldContainKeyValues() {
        AbstractMap.SimpleImmutableEntry<String, String> firstEntry = new AbstractMap.SimpleImmutableEntry<>("key1", "value1");
        AbstractMap.SimpleImmutableEntry<String, String> secondEntry = new AbstractMap.SimpleImmutableEntry<>("key2", "value2");
        AbstractMap.SimpleImmutableEntry<String, String> thirdEntry = new AbstractMap.SimpleImmutableEntry<>(firstEntry);

        assertThat(Stream.of(firstEntry, secondEntry, thirdEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key1", "value1"),
            tuple("key2", "value2"),
            tuple("key1", "value1"));
    }

    @Test
    public void givenImmutableEntryUsingJava9_whenVerifying_thenShouldContainKeyValues() {
        Map.Entry<String, String> entry = Map.entry("key", "value");

        assertThat(entry.getKey())
          .isEqualTo("key");
        assertThat(entry.getValue())
          .isEqualTo("value");
    }


    @Test
    public void givenEntriesWithApacheCommons_whenVerifying_thenShouldContainKeyValues() {
        Map.Entry<String, String> firstEntry = new DefaultMapEntry<>("key1", "value1");
        KeyValue<String, String> secondEntry = new DefaultMapEntry<>("key2", "value2");

        KeyValue<String, String> thirdEntry = new DefaultMapEntry<>(firstEntry);
        KeyValue<String, String> fourthEntry = new DefaultMapEntry<>(secondEntry);

        firstEntry.setValue("a different value");

        assertThat(firstEntry)
          .extracting("key", "value")
          .containsExactly("key1", "a different value");

        assertThat(Stream.of(secondEntry, thirdEntry, fourthEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key2", "value2"),
            tuple("key1", "value1"),
            tuple("key2", "value2"));
    }

    @Test
    public void givenImmutableEntriesWithApacheCommons_whenVerifying_thenShouldContainKeyValues() {
        Map.Entry<String, String> firstEntry = new UnmodifiableMapEntry<>("key1", "value1");
        KeyValue<String, String> secondEntry = new UnmodifiableMapEntry<>("key2", "value2");

        KeyValue<String, String> thirdEntry = new UnmodifiableMapEntry<>(firstEntry);
        KeyValue<String, String> fourthEntry = new UnmodifiableMapEntry<>(secondEntry);

        assertThat(firstEntry)
          .extracting("key", "value")
          .containsExactly("key1", "value1");

        assertThat(Stream.of(secondEntry, thirdEntry, fourthEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key2", "value2"),
            tuple("key1", "value1"),
            tuple("key2", "value2"));
    }


    @Test
    public void givenImmutableEntriesWithGuava_whenVerifying_thenShouldContainKeyValues() {
        Map.Entry<String, String> firstEntry = Maps.immutableEntry("key1", "value1");
        Map.Entry<String, String> secondEntry = Maps.immutableEntry("key2", "value2");

        assertThat(Stream.of(firstEntry, secondEntry))
          .extracting("key", "value")
          .containsExactly(
            tuple("key1", "value1"),
            tuple("key2", "value2"));
    }

}
