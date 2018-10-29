package com.baeldung.enummap;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.AbstractMap.SimpleEntry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class EnumMapUnitTest {
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Test
    public void whenContructedWithEnumType_ThenOnlyAcceptThatAsKey() {
        Map dayMap = new EnumMap<>(DayOfWeek.class);
        assertThatCode(
          () -> dayMap.put(TimeUnit.NANOSECONDS, "NANOSECONDS"))
          .isInstanceOf(ClassCastException.class);
    }

    @Test
    public void whenConstructedWithEnumMap_ThenSameKeyTypeAndInitialMappings() {
        EnumMap<DayOfWeek, String> activityMap = new EnumMap<>(DayOfWeek.class);
        activityMap.put(DayOfWeek.MONDAY, "Soccer");
        activityMap.put(DayOfWeek.TUESDAY, "Basketball");

        EnumMap<DayOfWeek, String> activityMapCopy = new EnumMap<>(activityMap);
        assertThat(activityMapCopy.size()).isEqualTo(2);
        assertThat(activityMapCopy.get(DayOfWeek.MONDAY))
          .isEqualTo("Soccer");
        assertThat(activityMapCopy.get(DayOfWeek.TUESDAY))
          .isEqualTo("Basketball");
    }

    @Test
    public void givenEmptyMap_whenConstructedWithMap_ThenException() {
        HashMap ordinaryMap = new HashMap();
        assertThatCode(() -> new EnumMap(ordinaryMap))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Specified map is empty");
    }

    @Test
    public void givenMapWithEntries_whenConstructedWithMap_ThenSucceed() {
        HashMap<DayOfWeek, String> ordinaryMap = new HashMap<>();
        ordinaryMap.put(DayOfWeek.MONDAY, "Soccer");
        ordinaryMap.put(DayOfWeek.TUESDAY, "Basketball");
        EnumMap<DayOfWeek, String> enumMap = new EnumMap<>(ordinaryMap);
        assertThat(enumMap.size()).isEqualTo(2);
        assertThat(enumMap.get(DayOfWeek.MONDAY)).isEqualTo("Soccer");
        assertThat(enumMap.get(DayOfWeek.TUESDAY)).isEqualTo("Basketball");
    }

    @Test
    public void givenMapWithMultiTypeEntries_whenConstructedWithMap_ThenException() {
        HashMap<Enum, String> ordinaryMap = new HashMap<>();
        ordinaryMap.put(DayOfWeek.MONDAY, "Soccer");
        ordinaryMap.put(TimeUnit.MILLISECONDS, "Other enum type");
        assertThatCode(() -> new EnumMap(ordinaryMap))
          .isInstanceOf(ClassCastException.class);
    }

    @Test
    public void whenPut_thenGet() {
        Map<DayOfWeek, String> activityMap = new EnumMap(DayOfWeek.class);
        activityMap.put(DayOfWeek.WEDNESDAY, "Hiking");
        activityMap.put(DayOfWeek.THURSDAY, null);
        assertThat(activityMap.get(DayOfWeek.WEDNESDAY)).isEqualTo("Hiking");
        assertThat(activityMap.get(DayOfWeek.THURSDAY)).isNull();
    }

    @Test
    public void givenMapping_whenContains_thenTrue() {
        EnumMap<DayOfWeek, String> activityMap = new EnumMap(DayOfWeek.class);
        assertThat(activityMap.containsKey(DayOfWeek.WEDNESDAY)).isFalse();
        assertThat(activityMap.containsValue("Hiking")).isFalse();
        activityMap.put(DayOfWeek.WEDNESDAY, "Hiking");
        assertThat(activityMap.containsKey(DayOfWeek.WEDNESDAY)).isTrue();
        assertThat(activityMap.containsValue("Hiking")).isTrue();

        assertThat(activityMap.containsKey(DayOfWeek.SATURDAY)).isFalse();
        assertThat(activityMap.containsValue(null)).isFalse();
        activityMap.put(DayOfWeek.SATURDAY, null);
        assertThat(activityMap.containsKey(DayOfWeek.SATURDAY)).isTrue();
        assertThat(activityMap.containsValue(null)).isTrue();
    }

    @Test
    public void whenRemove_thenRemoved() {
        EnumMap<DayOfWeek, String> activityMap = new EnumMap(DayOfWeek.class);

        activityMap.put(DayOfWeek.MONDAY, "Soccer");
        assertThat(activityMap.remove(DayOfWeek.MONDAY)).isEqualTo("Soccer");
        assertThat(activityMap.containsKey(DayOfWeek.MONDAY)).isFalse();

        activityMap.put(DayOfWeek.MONDAY, "Soccer");
        assertThat(activityMap.remove(DayOfWeek.MONDAY, "Hiking")).isEqualTo(false);
        assertThat(activityMap.remove(DayOfWeek.MONDAY, "Soccer")).isEqualTo(true);
    }

    @Test
    public void whenSubView_thenSubViewOrdered() {
        EnumMap<DayOfWeek, String> activityMap = new EnumMap(DayOfWeek.class);
        activityMap.put(DayOfWeek.THURSDAY, "Karate");
        activityMap.put(DayOfWeek.WEDNESDAY, "Hiking");
        activityMap.put(DayOfWeek.MONDAY, "Soccer");

        Collection<String> values = activityMap.values();
        assertThat(values).containsExactly("Soccer", "Hiking", "Karate");

        Set<DayOfWeek> keys = activityMap.keySet();
        assertThat(keys)
          .containsExactly(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY);

        assertThat(activityMap.entrySet())
          .containsExactly(
            new SimpleEntry(DayOfWeek.MONDAY, "Soccer"),
            new SimpleEntry(DayOfWeek.WEDNESDAY, "Hiking"),
            new SimpleEntry(DayOfWeek.THURSDAY, "Karate"));
    }

    @Test
    public void givenSubView_whenChange_thenReflected() {
        EnumMap<DayOfWeek, String> activityMap = new EnumMap(DayOfWeek.class);
        activityMap.put(DayOfWeek.THURSDAY, "Karate");
        activityMap.put(DayOfWeek.WEDNESDAY, "Hiking");
        activityMap.put(DayOfWeek.MONDAY, "Soccer");

        Collection<String> values = activityMap.values();
        assertThat(values).containsExactly("Soccer", "Hiking", "Karate");

        activityMap.put(DayOfWeek.TUESDAY, "Basketball");
        assertThat(values)
          .containsExactly("Soccer", "Basketball", "Hiking", "Karate");

        values.remove("Hiking");
        assertThat(activityMap.containsKey(DayOfWeek.WEDNESDAY)).isFalse();
        assertThat(activityMap.size()).isEqualTo(3);
    }
}
