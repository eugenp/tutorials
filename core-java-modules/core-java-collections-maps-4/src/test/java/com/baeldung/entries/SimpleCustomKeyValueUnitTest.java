package com.baeldung.entries;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class SimpleCustomKeyValueUnitTest {


    @Test
    public void givenModifiableEntries_whenVerifying_thenShouldContainKeyValues() {
        Map.Entry<String, String> firstEntry = new SimpleCustomKeyValue<>("key1", "value1");

        Map.Entry<String, String> secondEntry = new SimpleCustomKeyValue<>("key2", "value2");
        secondEntry.setValue("different value");

        Map<String, String> map = Map.ofEntries(firstEntry, secondEntry);

        assertThat(map)
          .isEqualTo(ImmutableMap.<String,String>builder()
            .put("key1", "value1")
            .put("key2", "different value")
            .build());
    }

}