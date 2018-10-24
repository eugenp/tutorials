package com.baeldung.enummap;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class EnumMapUnitTest {
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Test
    public void whenContructedWithEnumType_ThenOnlyAcceptThatAsKey() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);
        Map genericMap = dayMap;
        assertThatCode(() -> genericMap.put(TimeUnit.NANOSECONDS, "NANOSECONDS")).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void whenConstructedWithEnumMap_ThenSameKeyTypeAndInitialMappings() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);
        dayMap.put(DayOfWeek.MONDAY, "MONDAY");
        dayMap.put(DayOfWeek.TUESDAY, "TUESDAY");

        EnumMap<DayOfWeek, String> activityMapCopy = new EnumMap<>(dayMap);
        assertThat(activityMapCopy.size()).isEqualTo(2);
        assertThat(activityMapCopy.get(DayOfWeek.MONDAY)).isEqualTo("MONDAY");
        assertThat(activityMapCopy.get(DayOfWeek.TUESDAY)).isEqualTo("TUESDAY");
    }

    @Test
    public void givenEmptyMap_whenConstructedWithMap_ThenException() {
        HashMap ordinaryMap = new HashMap();
        assertThatCode(() -> new EnumMap(ordinaryMap)).isInstanceOf(IllegalArgumentException.class).hasMessage("Specified map is empty");
    }

    @Test
    public void givenMapWithEntries_whenConstructedWithMap_ThenSucceed() {
        HashMap ordinaryMap = new HashMap();
        ordinaryMap.put(DayOfWeek.MONDAY, "MONDAY");
        assertThatCode(() -> new EnumMap(ordinaryMap)).doesNotThrowAnyException();
    }

    @Test
    public void givenMapWithMultiTypeEntries_whenConstructedWithMap_ThenException() {
        HashMap ordinaryMap = new HashMap();
        ordinaryMap.put(DayOfWeek.MONDAY, "MONDAY");
        ordinaryMap.put(TimeUnit.MILLISECONDS, "MILLISECONDS");
        assertThatCode(() -> new EnumMap(ordinaryMap)).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void whenPut_thenGet() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap(DayOfWeek.class);
        dayMap.put(DayOfWeek.MONDAY, "MONDAY");
        dayMap.put(DayOfWeek.TUESDAY, null);
        assertThat(dayMap.get(DayOfWeek.MONDAY)).isEqualTo("MONDAY");
        assertThat(dayMap.get(DayOfWeek.TUESDAY)).isNull();
    }

    @Test
    public void givenMapping_whenContains_thenTrue() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap(DayOfWeek.class);
        assertThat(dayMap.containsKey(DayOfWeek.MONDAY)).isFalse();
        assertThat(dayMap.containsValue("MONDAY")).isFalse();
        dayMap.put(DayOfWeek.MONDAY, "MONDAY");
        assertThat(dayMap.containsKey(DayOfWeek.MONDAY)).isTrue();
        assertThat(dayMap.containsValue("MONDAY")).isTrue();

        assertThat(dayMap.containsKey(DayOfWeek.TUESDAY)).isFalse();
        assertThat(dayMap.containsValue(null)).isFalse();
        dayMap.put(DayOfWeek.TUESDAY, null);
        assertThat(dayMap.containsKey(DayOfWeek.TUESDAY)).isTrue();
        assertThat(dayMap.containsValue(null)).isTrue();
    }

    @Test
    public void whenRemove_thenRemoved() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap(DayOfWeek.class);

        dayMap.put(DayOfWeek.MONDAY, "MONDAY");
        assertThat(dayMap.remove(DayOfWeek.MONDAY)).isEqualTo("MONDAY");
        assertThat(dayMap.containsKey(DayOfWeek.MONDAY)).isFalse();

        dayMap.put(DayOfWeek.MONDAY, "MONDAY");
        assertThat(dayMap.remove(DayOfWeek.MONDAY, "AAA")).isEqualTo(false);
        assertThat(dayMap.remove(DayOfWeek.MONDAY, "MONDAY")).isEqualTo(true);
    }

    @Test
    public void whenSubView_thenSubViewOrdered() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap(DayOfWeek.class);
        dayMap.put(DayOfWeek.SATURDAY, "SATURDAY");
        dayMap.put(DayOfWeek.WEDNESDAY, "WEDNESDAY");
        dayMap.put(DayOfWeek.MONDAY, "MONDAY");

        Collection<String> values = dayMap.values();
        assertThat(values).containsExactly("MONDAY", "WEDNESDAY", "SATURDAY");

        Set<DayOfWeek> keys = dayMap.keySet();
        assertThat(keys).containsExactly(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY);

        assertThat(dayMap.entrySet()).containsExactly(new AbstractMap.SimpleEntry<>(DayOfWeek.MONDAY, "MONDAY"), new AbstractMap.SimpleEntry<>(DayOfWeek.WEDNESDAY, "WEDNESDAY"), new AbstractMap.SimpleEntry<>(DayOfWeek.SATURDAY, "SATURDAY"));
    }

    @Test
    public void givenSubView_whenChange_thenReflected() {
        EnumMap<DayOfWeek, String> dayMap = new EnumMap(DayOfWeek.class);
        dayMap.put(DayOfWeek.SATURDAY, "SATURDAY");
        dayMap.put(DayOfWeek.WEDNESDAY, "WEDNESDAY");
        dayMap.put(DayOfWeek.MONDAY, "MONDAY");

        Collection<String> values = dayMap.values();
        assertThat(values).containsExactly("MONDAY", "WEDNESDAY", "SATURDAY");

        dayMap.put(DayOfWeek.TUESDAY, "TUESDAY");
        assertThat(values).containsExactly("MONDAY", "TUESDAY", "WEDNESDAY", "SATURDAY");

        values.remove("WEDNESDAY");
        assertThat(dayMap.containsKey(DayOfWeek.WEDNESDAY)).isFalse();
        assertThat(dayMap.size()).isEqualTo(3);
    }
}
